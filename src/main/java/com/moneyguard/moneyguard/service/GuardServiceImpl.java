package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.GuardDAO;
import com.moneyguard.moneyguard.model.Category;
import com.moneyguard.moneyguard.model.Guard;
import com.moneyguard.moneyguard.model.ImportanceLevel;
import com.moneyguard.moneyguard.model.Tag;
import com.moneyguard.moneyguard.repository.*;
import com.moneyguard.moneyguard.request.CreateGuardRequest;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import com.moneyguard.moneyguard.request.UpdateGuardRequest;
import com.moneyguard.moneyguard.resource.Targetable;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.GuardDetailResponse;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("guardService")
public class GuardServiceImpl implements GuardService {

    @Autowired
    GuardRepository guardRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AuthService authService;

    @Override
    public GuardDAO create(CreateGuardRequest request) throws NotFoundException {
        Guard guard = new Guard();
        BeanUtils.copyProperties(request, guard);
        guard.setFrequencyType(request.getFrequency());
        guard.setUser(authService.getAuthUser());
        guard.setCreatedAt(new Date());
        guard.setUpdatedAt(new Date());
        guardRepository.save(guard);
        for (String uuid: request.getCategories()) {
                Category category = categoryRepository.findById(UUID.fromString(uuid));
                if (category != null) {
                    guard.addCategory(category);
                }
        }
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null) {
                guard.addTag(tag);
            }
        }
        guardRepository.save(guard);
        GuardDAO guardDAO = new GuardDAO();
        BeanUtils.copyProperties(guard, guardDAO);
        guardDAO.setFrequency(guard.getFrequencyType());
        return guardDAO;
    }

    @Override
    public GuardDetailResponse update(Guard guard, UpdateGuardRequest request) throws NotFoundException {
        BeanUtils.copyProperties(request, guard);
        guard.setFrequencyType(request.getFrequency());
        guard.setUpdatedAt(new Date());
        Set<Category> categories = guard.getCategories();
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null && !categories.contains(category)) {
                guard.addCategory(category);
            }
        }
        Set<Tag> tags = guard.getTags();
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null && !tags.contains(tag)) {
                guard.addTag(tag);
            }
        }
        guardRepository.saveAndFlush(guard);
        categories = new HashSet<>(guard.getCategories());
        tags = new HashSet<>(guard.getTags());
        for (Category category: categories) {
            if (Arrays.stream(request.getCategories()).noneMatch(category.getId().toString()::equals)) {
                guard.removeCategory(category.getId());
            }
        }
        for (Tag tag: tags) {
            if (Arrays.stream(request.getTags()).noneMatch(tag.getId().toString()::equals)) {
                guard.removeTag(tag.getId());
            }
        }
        guardRepository.save(guard);
        return new GuardDetailResponse(guard);
    }

    @Override
    public Boolean delete(Guard guard) {
        guardRepository.delete(guard);
        return true;
    }

    @Override
    public GuardDetailResponse get(Guard guard) {
        return new GuardDetailResponse(guard);
    }

    @Override
    public List<GuardDAO> list(Integer page, String search) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<Guard> pageData = guardRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId(), pageable);
        List<Guard> guards = pageData.getContent();

        List<GuardDAO> daoList = new ArrayList<>();
        guards.forEach(g -> {
            GuardDAO guardDAO = new GuardDAO();
            BeanUtils.copyProperties(g, guardDAO);
            guardDAO.setFrequency(g.getFrequencyType());
            daoList.add(guardDAO);
        });
        return daoList;
    }

    @Override
    public List<DropdownListResource> getSelectData(String search) {
        List<Guard> guards = guardRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId());
        List<DropdownListResource> dropdownListData = new ArrayList<>();
        guards.forEach(c -> {
            DropdownListResource listItem = new DropdownListResource();
            listItem.setText(c.getName());
            listItem.setValue(c.getId().toString());
            dropdownListData.add(listItem);
        });
        return dropdownListData;
    }

    @Override
    public Targetable getCurrentState(Guard guard) {
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date oneMonthAgo = cal.getTime();

        long DAY_IN_MS = 1000 * 60 * 60 * 24;
        Date oneWeekAgo = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));

        cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, -1);
        Date oneYearAgo = cal.getTime();

        ArrayList<String> categoryIds = new ArrayList<String>();
        ArrayList<String> tagIds = new ArrayList<String>();
        ArrayList<String> importanceLevels = new ArrayList<String>();
        ArrayList<String> types = new ArrayList<String>();

        for(Category category:guard.getCategories()) {
            categoryIds.add(category.getId().toString());
        }

        for(Tag tag:guard.getTags()) {
            tagIds.add(tag.getId().toString());
        }
        if (guard.getImportanceLevel() != null) {
            importanceLevels.add(guard.getImportanceLevel().getId().toString());
        }

        if (guard.getType() != null) {
            types.add(guard.getType().toString());
        } else {
            types.add("1");
            types.add("2");
            types.add("3");
        }

        Date startDate = new Date();
        switch (guard.getFrequencyType()) {
            case 1:
                startDate = oneWeekAgo;
                break;
            case 2:
                startDate = oneMonthAgo;
                break;
            case 3:
                startDate = oneYearAgo;
                break;
        }

        Double total = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                0,
                "",
                tagIds.toArray(new String[0]),
                categoryIds.toArray(new String[0]),
                importanceLevels.toArray(new String[0]),
                types.toArray(new String[0]),
                startDate,
                now,
                "name",
                "ASC"
        ));

        Targetable result = new Targetable();
        result.setName(guard.getName());
        result.setStart(startDate);
        result.setEnd(now);
        result.setTarget(guard.getThreshold());
        if (guard.getThreshold() != null && total != null) {
            result.setPercentage((float) (total / guard.getThreshold() * 100));
        } else {
            result.setPercentage((float) 0);
        }
        result.setId(guard.getId());
        result.setValue(total);
        result.setType((short) 1);
        return result;
    }

}
