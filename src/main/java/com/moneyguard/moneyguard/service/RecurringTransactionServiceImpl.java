package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.RecurringTransactionDAO;
import com.moneyguard.moneyguard.dao.TransactionDAO;
import com.moneyguard.moneyguard.model.*;
import com.moneyguard.moneyguard.repository.*;
import com.moneyguard.moneyguard.request.CreateRecurringTransactionRequest;
import com.moneyguard.moneyguard.request.UpdateRecurringTransactionRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.RecurringTransactionDetailResponse;
import com.moneyguard.moneyguard.utils.Utils;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

@Service("recurringTransactionService")
public class RecurringTransactionServiceImpl implements RecurringTransactionService {

    @Autowired
    RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AuthService authService;

    @Autowired
    ImportanceLevelRepository importanceLevelRepository;

    @Override
    public RecurringTransactionDAO create(CreateRecurringTransactionRequest request) throws NotFoundException {
        RecurringTransaction recurringTransaction = new RecurringTransaction();
        BeanUtils.copyProperties(request, recurringTransaction);
        if (request.getType() == 2) {
            ImportanceLevel importanceLevel = importanceLevelRepository.findById(UUID.fromString(request.getImportanceLevel()));
            if (request.getType() == 2) {
                if (importanceLevel == null) {
                    throw new NotFoundException("Invalid importance level");
                }
                recurringTransaction.setImportanceLevel(importanceLevel);
            } else {
                recurringTransaction.setImportanceLevel(null);
            }
            recurringTransaction.setImportanceLevel(importanceLevel);
        }
        recurringTransaction.setFrequencyType(request.getFrequency());
        recurringTransaction.setUser(authService.getAuthUser());
        recurringTransaction.setCreatedAt(new Date());
        recurringTransaction.setUpdatedAt(new Date());
        recurringTransaction.setLastProcessed(request.getStartDate());
        recurringTransactionRepository.save(recurringTransaction);
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null) {
                recurringTransaction.addCategory(category);
            }
        }
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null) {
                recurringTransaction.addTag(tag);
            }
        }
        recurringTransactionRepository.save(recurringTransaction);
        generateTransactions(recurringTransaction);
        RecurringTransactionDAO recurringTransactionDAO = new RecurringTransactionDAO();
        BeanUtils.copyProperties(recurringTransaction, recurringTransactionDAO);
        return recurringTransactionDAO;
    }

    @Override
    public RecurringTransactionDetailResponse update(RecurringTransaction recurringTransaction, UpdateRecurringTransactionRequest request) throws NotFoundException {
        BeanUtils.copyProperties(request, recurringTransaction);
        recurringTransaction.setFrequencyType(request.getFrequency());
        recurringTransaction.setUpdatedAt(new Date());
        if (request.getType() == 2) {
            ImportanceLevel importanceLevel = importanceLevelRepository.findById(UUID.fromString(request.getImportanceLevel()));
            if (importanceLevel == null) {
                throw new NotFoundException("Invalid importance level");
            }
            recurringTransaction.setImportanceLevel(importanceLevel);
        } else {
            recurringTransaction.setImportanceLevel(null);
        }
        Set<Category> categories = recurringTransaction.getCategories();
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null && !categories.contains(category)) {
                recurringTransaction.addCategory(category);
            }
        }
        Set<Tag> tags = recurringTransaction.getTags();
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null && !tags.contains(tag)) {
                recurringTransaction.addTag(tag);
            }
        }
        recurringTransactionRepository.saveAndFlush(recurringTransaction);
        categories = new HashSet<>(recurringTransaction.getCategories());
        tags = new HashSet<>(recurringTransaction.getTags());
        for (Category category: categories) {
            if (Arrays.stream(request.getCategories()).noneMatch(category.getId().toString()::equals)) {
                recurringTransaction.removeCategory(category.getId());
            }
        }
        for (Tag tag: tags) {
            if (Arrays.stream(request.getTags()).noneMatch(tag.getId().toString()::equals)) {
                recurringTransaction.removeTag(tag.getId());
            }
        }
        recurringTransactionRepository.save(recurringTransaction);
        return new RecurringTransactionDetailResponse(recurringTransaction);
    }

    @Override
    public Boolean delete(RecurringTransaction recurringTransaction) {
        recurringTransactionRepository.delete(recurringTransaction);
        return true;
    }

    @Override
    public RecurringTransactionDetailResponse get(RecurringTransaction recurringTransaction) {
        return new RecurringTransactionDetailResponse(recurringTransaction);
    }

    @Override
    public List<RecurringTransactionDAO> list(Integer page, String search) {
        Pageable pageable = PageRequest.of(page, 25);
        Page<RecurringTransaction> pageData = recurringTransactionRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId(), pageable);
        List<RecurringTransaction> guards = pageData.getContent();

        List<RecurringTransactionDAO> daoList = new ArrayList<>();
        guards.forEach(g -> {
            RecurringTransactionDAO recurringTransactionDAO = new RecurringTransactionDAO();
            BeanUtils.copyProperties(g, recurringTransactionDAO);
            daoList.add(recurringTransactionDAO);
        });
        return daoList;
    }

    @Override
    public List<DropdownListResource> getSelectData(String search) {
        List<RecurringTransaction> guards = recurringTransactionRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId());
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
    public Boolean generateTransactions(RecurringTransaction recurringTransaction) {
        List<Date> dates = Utils.getDates(
                recurringTransaction.getLastProcessed(),
                recurringTransaction.getStartDate(),
                recurringTransaction.getEndDate(),
                recurringTransaction.getFrequencyType(),
                recurringTransaction.getFrequencyBasis()
        );
        dates.forEach(d -> {
            System.out.println(recurringTransaction.getName()+" - "+Utils.format(d));
            Transaction transaction = new Transaction();
            BeanUtils.copyProperties(recurringTransaction, transaction);
            if (recurringTransaction.getType() == 2) {
                ImportanceLevel importanceLevel = importanceLevelRepository.findById(UUID.fromString(recurringTransaction.getImportanceLevel().getId().toString()));
                if (importanceLevel == null) {
                    try {
                        throw new NotFoundException("Invalid importance level");
                    } catch (NotFoundException e) {
                        e.printStackTrace();
                    }
                }
                transaction.setImportanceLevel(importanceLevel);
            } else {
                transaction.setImportanceLevel(null);
            }
            transaction.setUser(recurringTransaction.getUser());
            transaction.setCreatedAt(d);
            transaction.setUpdatedAt(d);
            transactionRepository.save(transaction);
            for (Category category: recurringTransaction.getCategories()) {
                if (category != null) {
                    transaction.addCategory(category);
                }
            }
            for (Tag tag: recurringTransaction.getTags()) {
                if (tag != null) {
                    transaction.addTag(tag);
                }
            }
            transactionRepository.save(transaction);
        });
        long time = new Date().getTime();
        Date today = new Date((time - time % (24 * 60 * 60 * 1000)) - 1);
        recurringTransaction.setLastProcessed(today);
        recurringTransactionRepository.save(recurringTransaction);
        return null;
    }
}
