package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.TransactionDAO;
import com.moneyguard.moneyguard.model.*;
import com.moneyguard.moneyguard.repository.*;
import com.moneyguard.moneyguard.request.CreateTransactionRequest;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import com.moneyguard.moneyguard.request.UpdateTransactionRequest;
import com.moneyguard.moneyguard.response.*;
import javassist.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    AuthService authService;

    @Autowired
    ImportanceLevelRepository importanceLevelRepository;

    @Override
    public TransactionDAO create(CreateTransactionRequest request) throws NotFoundException {
        Transaction transaction = new Transaction();
        BeanUtils.copyProperties(request, transaction);
        if (request.getType() == 2) {
            ImportanceLevel importanceLevel = importanceLevelRepository.findById(UUID.fromString(request.getImportanceLevel()));
            if (importanceLevel == null) {
                throw new NotFoundException("Invalid importance level");
            }
            transaction.setImportanceLevel(importanceLevel);
        } else {
            transaction.setImportanceLevel(null);
        }
        transaction.setUser(authService.getAuthUser());
        transaction.setCreatedAt(new Date());
        transaction.setUpdatedAt(new Date());
        transactionRepository.save(transaction);
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null) {
                transaction.addCategory(category);
            }
        }
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null) {
                transaction.addTag(tag);
            }
        }
        transactionRepository.save(transaction);
        TransactionDAO transactionDAO = new TransactionDAO();
        BeanUtils.copyProperties(transaction, transactionDAO);
        return transactionDAO;
    }

    @Override
    public TransactionDetailResponse update(Transaction transaction, UpdateTransactionRequest request) throws NotFoundException {
        BeanUtils.copyProperties(request, transaction);
        transaction.setUpdatedAt(new Date());
        if (request.getType() == 2) {
            ImportanceLevel importanceLevel = importanceLevelRepository.findById(UUID.fromString(request.getImportanceLevel()));
            if (importanceLevel == null) {
                throw new NotFoundException("Invalid importance level");
            }
            transaction.setImportanceLevel(importanceLevel);
        } else {
            transaction.setImportanceLevel(null);
        }
        Set<Category> categories = transaction.getCategories();
        for (String uuid: request.getCategories()) {
            Category category = categoryRepository.findById(UUID.fromString(uuid));
            if (category != null && !categories.contains(category)) {
                transaction.addCategory(category);
            }
        }
        Set<Tag> tags = transaction.getTags();
        for (String uuid: request.getTags()) {
            Tag tag = tagRepository.findById(UUID.fromString(uuid));
            if (tag != null && !tags.contains(tag)) {
                transaction.addTag(tag);
            }
        }
        transactionRepository.saveAndFlush(transaction);
        categories = new HashSet<>(transaction.getCategories());
        tags = new HashSet<>(transaction.getTags());
        for (Category category: categories) {
            if (Arrays.stream(request.getCategories()).noneMatch(category.getId().toString()::equals)) {
                transaction.removeCategory(category.getId());
            }
        }
        for (Tag tag: tags) {
            if (Arrays.stream(request.getTags()).noneMatch(tag.getId().toString()::equals)) {
                transaction.removeTag(tag.getId());
            }
        }
        transactionRepository.save(transaction);
        return new TransactionDetailResponse(transaction);
    }

    @Override
    public Boolean delete(Transaction transaction) {
        transactionRepository.delete(transaction);
        return true;

    }

    @Override
    public TransactionDetailResponse get(Transaction transaction) {
        return new TransactionDetailResponse(transaction);
    }

    @Override
    public TransactionsSearchResultResponse list(RetrieveTransactionsRequest request) {
        List<Transaction> transactions = transactionRepository.advancedSearch(request);
        Double total = transactionRepository.getTotal(request);
        List<TransactionListResponse> list = new ArrayList<>();
        transactions.forEach(t -> {
            TransactionListResponse transactionListResponse = new TransactionListResponse(t);
            list.add(transactionListResponse);
        });
        TransactionsSearchResultResponse transactionsSearchResultResponse = new TransactionsSearchResultResponse();
        transactionsSearchResultResponse.setList(list);
        if (total != null) {
            transactionsSearchResultResponse.setTotal(total);
        }
        return transactionsSearchResultResponse;
    }

    @Override
    public List<DropdownListResource> getSelectData(String search) {
        List<Transaction> guards = transactionRepository.findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(search, authService.getAuthUser().getId());
        List<DropdownListResource> dropdownListData = new ArrayList<>();
        guards.forEach(c -> {
            DropdownListResource listItem = new DropdownListResource();
            listItem.setText(c.getName());
            listItem.setValue(c.getId().toString());
            dropdownListData.add(listItem);
        });
        return dropdownListData;
    }
}
