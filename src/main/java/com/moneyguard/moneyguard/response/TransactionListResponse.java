package com.moneyguard.moneyguard.response;

import com.moneyguard.moneyguard.dao.CategoryDAO;
import com.moneyguard.moneyguard.dao.ImportanceLevelDAO;
import com.moneyguard.moneyguard.dao.TagDAO;
import com.moneyguard.moneyguard.model.Transaction;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class TransactionListResponse {

    private UUID id;
    private String name;
    private ImportanceLevelDAO importanceLevel;
    private Float amount;
    private Date createdAt;
    private Date updatedAt;
    private Short type;
    private Set<CategoryDAO> categories = new HashSet<>();
    private Set<TagDAO> tags = new HashSet<>();

    public TransactionListResponse(Transaction transaction) {
        BeanUtils.copyProperties(transaction, this);
        if (transaction.getImportanceLevel() != null) {
            ImportanceLevelDAO importanceLevelDAO = new ImportanceLevelDAO();
            BeanUtils.copyProperties(transaction.getImportanceLevel(), importanceLevelDAO);
            setImportanceLevel(importanceLevelDAO);
        }
        if (transaction.getCategories() != null) {
            transaction.getCategories().forEach(e -> {
                CategoryDAO categoryDAO = new CategoryDAO();
                BeanUtils.copyProperties(e, categoryDAO);
                categories.add(categoryDAO);
            });
        }
        if (transaction.getTags() != null) {
            transaction.getTags().forEach(e -> {
                TagDAO tagDAO = new TagDAO();
                BeanUtils.copyProperties(e, tagDAO);
                tags.add(tagDAO);
            });
        }
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
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

    public ImportanceLevelDAO getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(ImportanceLevelDAO importanceLevel) {
        this.importanceLevel = importanceLevel;
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

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

}
