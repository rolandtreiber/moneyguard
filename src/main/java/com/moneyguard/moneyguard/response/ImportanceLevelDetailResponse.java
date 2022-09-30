package com.moneyguard.moneyguard.response;

import com.moneyguard.moneyguard.dao.*;
import com.moneyguard.moneyguard.model.ImportanceLevel;
import com.moneyguard.moneyguard.model.Transaction;
import com.moneyguard.moneyguard.repository.TransactionRepository;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class ImportanceLevelDetailResponse {

    private UUID id;
    private String name;
    private Short level;
    private Date createdAt;
    private Date updatedAt;
    private Set<TransactionDAO> transactions = new HashSet<>();

    public ImportanceLevelDetailResponse(ImportanceLevel importanceLevel, TransactionRepository transactionRepository) {
        BeanUtils.copyProperties(importanceLevel, this);
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date oneMonthAgo = cal.getTime();
        RetrieveTransactionsRequest request = new RetrieveTransactionsRequest();
        request.setCategories(new String[]{importanceLevel.getId().toString()});

        request.setStartDate(oneMonthAgo);
        request.setEndDate(now);
        request.setPage(0);
        List<Transaction> fetchedTransactions = transactionRepository.advancedSearch(new RetrieveTransactionsRequest(
                0,
                "",
                new String[]{},
                new String[]{},
                new String[]{importanceLevel.getId().toString()},
                new String[]{"1", "2", "3"},
                oneMonthAgo,
                now,
                "date",
                "DESC"
        ));
        fetchedTransactions.forEach(e -> {
            TransactionDAO transactionDAO = new TransactionDAO();
            BeanUtils.copyProperties(e, transactionDAO);
            transactions.add(transactionDAO);
        });
    }

    public Set<TransactionDAO> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<TransactionDAO> transactions) {
        this.transactions = transactions;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
