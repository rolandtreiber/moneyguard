package com.moneyguard.moneyguard.response;

import com.moneyguard.moneyguard.dao.CategoryDAO;
import com.moneyguard.moneyguard.dao.TagDAO;
import com.moneyguard.moneyguard.model.ImportanceLevel;
import com.moneyguard.moneyguard.model.SavingGoal;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SavingGoalDetailResponse {

    private UUID id;
    private String name;
    private ImportanceLevel importanceLevel;
    private Short frequency;
    private Integer frequencyBasis;
    private Float target;
    private Date startDate;
    private Date endDate;
    private Date createdAt;
    private Date updatedAt;
    private Set<CategoryDAO> categories = new HashSet<>();
    private Set<TagDAO> tags = new HashSet<>();

    public SavingGoalDetailResponse(SavingGoal savingGoal) {
        BeanUtils.copyProperties(savingGoal, this);
        setFrequency(savingGoal.getFrequencyType());
        if (savingGoal.getCategories() != null) {
            savingGoal.getCategories().forEach(e -> {
                CategoryDAO categoryDAO = new CategoryDAO();
                BeanUtils.copyProperties(e, categoryDAO);
                categories.add(categoryDAO);
            });
        }
        if (savingGoal.getTags() != null) {
            savingGoal.getTags().forEach(e -> {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImportanceLevel getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(ImportanceLevel importanceLevel) {
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

    public Float getTarget() {
        return target;
    }

    public void setTarget(Float target) {
        this.target = target;
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
