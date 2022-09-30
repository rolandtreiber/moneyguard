package com.moneyguard.moneyguard.response;

import com.moneyguard.moneyguard.dao.*;
import com.moneyguard.moneyguard.model.Tag;
import com.moneyguard.moneyguard.model.Transaction;
import com.moneyguard.moneyguard.repository.TransactionRepository;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import org.springframework.beans.BeanUtils;

import java.util.*;

public class TagDetailResponse {

    private UUID id;
    private String name;
    private Date createdAt;
    private Date updatedAt;
    private Short type;
    private Set<TransactionDAO> transactions = new HashSet<>();
    private Set<GuardDAO> guards = new HashSet<>();
    private Set<RecurringTransactionDAO> recurringTransactions = new HashSet<>();
    private Set<SavingGoalDAO> savingGoals = new HashSet<>();
    private Set<TemplateDAO> templates = new HashSet<>();

    public TagDetailResponse(Tag tag, TransactionRepository transactionRepository) {
        this.setId(tag.getId());
        this.setName(tag.getName());
        this.setType(tag.getType());
        this.setCreatedAt(tag.getCreatedAt());
        this.setUpdatedAt(tag.getUpdatedAt());
        if (tag.getGuards() != null) {
            tag.getGuards().forEach(e -> {
                GuardDAO guardDAO = new GuardDAO();
                BeanUtils.copyProperties(e, guardDAO);
                guards.add(guardDAO);
            });
        }

        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -1);
        Date oneMonthAgo = cal.getTime();
        RetrieveTransactionsRequest request = new RetrieveTransactionsRequest();
        request.setCategories(new String[]{tag.getId().toString()});

        request.setStartDate(oneMonthAgo);
        request.setEndDate(now);
        request.setPage(0);
        List<Transaction> fetchedTransactions = transactionRepository.advancedSearch(new RetrieveTransactionsRequest(
                0,
                "",
                new String[]{tag.getId().toString()},
                new String[]{},
                new String[]{},
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

        if (tag.getRecurringTransactions() != null) {
            tag.getRecurringTransactions().forEach(e -> {
                RecurringTransactionDAO recurringTransactionDAO = new RecurringTransactionDAO();
                BeanUtils.copyProperties(e, recurringTransactionDAO);
                recurringTransactions.add(recurringTransactionDAO);
            });
        }

        if (tag.getSavingGoals() != null) {
            tag.getSavingGoals().forEach(e -> {
                SavingGoalDAO savingGoalDAO = new SavingGoalDAO();
                BeanUtils.copyProperties(e, savingGoalDAO);
                savingGoals.add(savingGoalDAO);
            });
        }

        if (tag.getTemplates() != null) {
            tag.getTemplates().forEach(e -> {
                TemplateDAO templateDAO = new TemplateDAO();
                BeanUtils.copyProperties(e, templateDAO);
                templates.add(templateDAO);
            });
        }

    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
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

    public Set<TransactionDAO> getTransactions() {
        return transactions;
    }

    public Set<GuardDAO> getGuards() {
        return guards;
    }

    public Set<RecurringTransactionDAO> getRecurringTransactions() {
        return recurringTransactions;
    }

    public Set<SavingGoalDAO> getSavingGoals() {
        return savingGoals;
    }

    public Set<TemplateDAO> getTemplates() {
        return templates;
    }
}
