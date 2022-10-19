package com.moneyguard.moneyguard.response;

import com.moneyguard.moneyguard.dao.CategoryDAO;
import com.moneyguard.moneyguard.dao.ImportanceLevelDAO;
import com.moneyguard.moneyguard.dao.TagDAO;
import com.moneyguard.moneyguard.model.RecurringTransaction;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class RecurringTransactionDetailResponse {

    private UUID id;
    private String name;
    private ImportanceLevelDAO importanceLevel;
    private Short frequency;
    private Integer frequencyBasis;
    private Float amount;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
    private Short type;
    private Set<CategoryDAO> categories = new HashSet<>();
    private Set<TagDAO> tags = new HashSet<>();

    public RecurringTransactionDetailResponse(RecurringTransaction recurringTransaction) {
        BeanUtils.copyProperties(recurringTransaction, this);
        setFrequency(recurringTransaction.getFrequencyType());
        if (recurringTransaction.getType() == 2) {
            ImportanceLevelDAO importanceLevelDAO = new ImportanceLevelDAO();
            BeanUtils.copyProperties(recurringTransaction.getImportanceLevel(), importanceLevelDAO);
            setImportanceLevel(importanceLevelDAO);
        }
        if (recurringTransaction.getCategories() != null) {
            recurringTransaction.getCategories().forEach(e -> {
                CategoryDAO categoryDAO = new CategoryDAO();
                BeanUtils.copyProperties(e, categoryDAO);
                categories.add(categoryDAO);
            });
        }
        if (recurringTransaction.getTags() != null) {
            recurringTransaction.getTags().forEach(e -> {
                TagDAO tagDAO = new TagDAO();
                BeanUtils.copyProperties(e, tagDAO);
                tags.add(tagDAO);
            });
        }
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImportanceLevelDAO getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(ImportanceLevelDAO importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    public Short getFrequency() {
        return frequency;
    }

    public void setFrequency(Short frequency) {
        this.frequency = frequency;
    }

    public Integer getFrequencyBasis() {
        return frequencyBasis;
    }

    public void setFrequencyBasis(Integer frequencyBasis) {
        this.frequencyBasis = frequencyBasis;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public Set<CategoryDAO> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoryDAO> categories) {
        this.categories = categories;
    }

    public Set<TagDAO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDAO> tags) {
        this.tags = tags;
    }
}
