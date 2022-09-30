package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.SavingGoalDAO;
import com.moneyguard.moneyguard.model.Category;
import com.moneyguard.moneyguard.model.SavingGoal;
import com.moneyguard.moneyguard.model.Tag;
import com.moneyguard.moneyguard.repository.CategoryRepository;
import com.moneyguard.moneyguard.repository.SavingGoalRepository;
import com.moneyguard.moneyguard.repository.TagRepository;
import com.moneyguard.moneyguard.repository.TransactionRepository;
import com.moneyguard.moneyguard.request.CreateSavingGoalRequest;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import com.moneyguard.moneyguard.request.UpdateSavingGoalRequest;
import com.moneyguard.moneyguard.resource.Targetable;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.SavingGoalDetailResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("savingGoalService")
public class SavingGoalServiceImpl implements SavingGoalService {

    @Autowired
    SavingGoalRepository savingGoalRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AuthService authService;

    @Override
    public SavingGoalDAO create(CreateSavingGoalRequest request) {
        SavingGoal savingGoal = new SavingGoal();
        BeanUtils.copyProperties(request, savingGoal);
        savingGoal.setFrequencyType(request.getFrequency());
        savingGoal.setUser(authService.getAuthUser());
        savingGoal.setCreatedAt(new Date());
        savingGoal.setUpdatedAt(new Date());
        savingGoalRepository.save(savingGoal);
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null) {
                savingGoal.addCategory(category);
            }
        }
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null) {
                savingGoal.addTag(tag);
            }
        }
        savingGoalRepository.save(savingGoal);
        SavingGoalDAO savingGoalDAO = new SavingGoalDAO();
        BeanUtils.copyProperties(savingGoal, savingGoalDAO);
        return savingGoalDAO;
    }

    @Override
    public SavingGoalDetailResponse update(SavingGoal savingGoal, UpdateSavingGoalRequest request) {
        BeanUtils.copyProperties(request, savingGoal);
        savingGoal.setFrequencyType(request.getFrequency());
        savingGoal.setUpdatedAt(new Date());
        Set<Category> categories = savingGoal.getCategories();
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null && !categories.contains(category)) {
                savingGoal.addCategory(category);
            }
        }
        Set<Tag> tags = savingGoal.getTags();
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null && !tags.contains(tag)) {
                savingGoal.addTag(tag);
            }
        }
        savingGoalRepository.saveAndFlush(savingGoal);
        categories = new HashSet<>(savingGoal.getCategories());
        tags = new HashSet<>(savingGoal.getTags());
        for (Category category: categories) {
            if (Arrays.stream(request.getCategories()).noneMatch(category.getId().toString()::equals)) {
                savingGoal.removeCategory(category.getId());
            }
        }
        for (Tag tag: tags) {
            if (Arrays.stream(request.getTags()).noneMatch(tag.getId().toString()::equals)) {
                savingGoal.removeTag(tag.getId());
            }
        }
        savingGoalRepository.save(savingGoal);
        return new SavingGoalDetailResponse(savingGoal);
    }

    @Override
    public Boolean delete(SavingGoal savingGoal) {
        savingGoalRepository.delete(savingGoal);
        return true;
    }

    @Override
    public SavingGoalDetailResponse get(SavingGoal savingGoal) {
        return new SavingGoalDetailResponse(savingGoal);
    }

    @Override
    public List<SavingGoalDAO> list(Integer page, String search) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<SavingGoal> pageData = savingGoalRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId(), pageable);
        List<SavingGoal> savingGoals = pageData.getContent();

        List<SavingGoalDAO> daoList = new ArrayList<>();
        savingGoals.forEach(g -> {
            SavingGoalDAO savingGoalDAO = new SavingGoalDAO();
            BeanUtils.copyProperties(g, savingGoalDAO);
            daoList.add(savingGoalDAO);
        });
        return daoList;
    }

    @Override
    public List<DropdownListResource> getSelectData(String search) {
        List<SavingGoal> savingGoals = savingGoalRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId());
        List<DropdownListResource> dropdownListData = new ArrayList<>();
        savingGoals.forEach(c -> {
            DropdownListResource listItem = new DropdownListResource();
            listItem.setText(c.getName());
            listItem.setValue(c.getId().toString());
            dropdownListData.add(listItem);
        });
        return dropdownListData;
    }

    @Override
    public Targetable getCurrentState(SavingGoal savingGoal) {
        ArrayList<String> categoryIds = new ArrayList<String>();
        ArrayList<String> tagIds = new ArrayList<String>();
        ArrayList<String> importanceLevels = new ArrayList<String>();
        ArrayList<String> types = new ArrayList<String>();

        for(Category category:savingGoal.getCategories()) {
            categoryIds.add(category.getId().toString());
        }

        for(Tag tag:savingGoal.getTags()) {
            tagIds.add(tag.getId().toString());
        }

        types.add("3");

        Double total = transactionRepository.getTotal(new RetrieveTransactionsRequest(
                0,
                "",
                tagIds.toArray(new String[0]),
                categoryIds.toArray(new String[0]),
                importanceLevels.toArray(new String[0]),
                types.toArray(new String[0]),
                savingGoal.getStartDate(),
                savingGoal.getEndDate(),
                "name",
                "ASC"
        ));

        Targetable result = new Targetable();
        result.setName(savingGoal.getName());
        result.setStart(savingGoal.getStartDate());
        result.setEnd(savingGoal.getEndDate());
        result.setTarget((double) savingGoal.getTarget());
        if (savingGoal.getTarget() != null && total != null) {
            result.setPercentage((float) (total / savingGoal.getTarget() * 100));
        } else {
            result.setPercentage((float) 0);
        }
        result.setId(savingGoal.getId());
        result.setValue(total);
        result.setType((short) 1);
        return result;
    }

}
