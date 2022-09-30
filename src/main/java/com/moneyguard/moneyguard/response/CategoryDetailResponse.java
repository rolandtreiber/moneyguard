package com.moneyguard.moneyguard.response;

import com.moneyguard.moneyguard.dao.*;
import com.moneyguard.moneyguard.model.*;
import com.moneyguard.moneyguard.repository.TransactionRepository;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class CategoryDetailResponse {

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

    public CategoryDetailResponse(Category category, TransactionRepository transactionRepository) {
        this.setId(category.getId());
        this.setName(category.getName());
        this.setType(category.getType());
        this.setCreatedAt(category.getCreatedAt());
        this.setUpdatedAt(category.getUpdatedAt());
        if (category.getGuards() != null) {
            category.getGuards().forEach(e -> {
                System.out.println(e);
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
            request.setCategories(new String[]{category.getId().toString()});

            request.setStartDate(oneMonthAgo);
            request.setEndDate(now);
            request.setPage(0);
            List<Transaction> fetchedTransactions = transactionRepository.advancedSearch(new RetrieveTransactionsRequest(
                    0,
                    "",
                    new String[]{},
                    new String[]{category.getId().toString()},
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

        if (category.getRecurringTransactions() != null) {
            category.getRecurringTransactions().forEach(e -> {
                RecurringTransactionDAO recurringTransactionDAO = new RecurringTransactionDAO();
                BeanUtils.copyProperties(e, recurringTransactionDAO);
                recurringTransactions.add(recurringTransactionDAO);
            });
        }

        if (category.getSavingGoals() != null) {
            category.getSavingGoals().forEach(e -> {
                SavingGoalDAO savingGoalDAO = new SavingGoalDAO();
                BeanUtils.copyProperties(e, savingGoalDAO);
                savingGoals.add(savingGoalDAO);
            });
        }

        if (category.getTemplates() != null) {
            category.getTemplates().forEach(e -> {
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
