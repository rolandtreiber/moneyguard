package com.moneyguard.moneyguard.model;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(	name = "recurring_transactions")
public class RecurringTransaction {

    public RecurringTransaction() {
    }

    @Id
    @Type(type = "uuid-char")
    private final UUID id = UUID.randomUUID();
    private String name;
    private Date startDate;
    private Date endDate;
    private Float amount;
    private Short frequencyType;
    private Integer frequencyBasis;
    private Date lastProcessed;
    private Short type;
    private Date createdAt;
    private Date updatedAt;

    @ManyToOne(cascade = {CascadeType.PERSIST}, fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="recurring_transaction_category",
            joinColumns = @JoinColumn(name="recurring_transaction_id"),
            inverseJoinColumns = @JoinColumn(name="category_id"))
    private Set<Category> categories = new HashSet<>();

    @ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="recurring_transaction_tag",
            joinColumns = @JoinColumn(name="recurring_transaction_id"),
            inverseJoinColumns = @JoinColumn(name="tag_id"))
    private Set<Tag> tags = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name="importance_level_id")
    private ImportanceLevel importanceLevel;

    public ImportanceLevel getImportanceLevel() {
        return importanceLevel;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public Date getLastProcessed() {
        return lastProcessed;
    }

    public void setLastProcessed(Date lastProcessed) {
        this.lastProcessed = lastProcessed;
    }

    public void setImportanceLevel(ImportanceLevel importanceLevel) {
        this.importanceLevel = importanceLevel;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Short getFrequencyType() {
        return frequencyType;
    }

    public void setFrequencyType(Short frequencyType) {
        this.frequencyType = frequencyType;
    }

    public Integer getFrequencyBasis() {
        return frequencyBasis;
    }

    public void setFrequencyBasis(Integer frequencyBasis) {
        this.frequencyBasis = frequencyBasis;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public void addCategory(Category category) {
        this.categories.add(category);
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
    }

    public void removeCategory(UUID categoryId) {
        Category category = this.categories.stream().filter(c -> c.getId() == categoryId).findFirst().orElse(null);
        if (category != null) {
            this.categories.remove(category);
            category.getRecurringTransactions().remove(this);
        }
    }

    public void removeTag(UUID tagId) {
        Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if (tag != null) {
            this.tags.remove(tag);
            tag.getRecurringTransactions().remove(this);
        }
    }

    @Override
    public String toString() {
        return "RecurringTransaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", frequency_type=" + frequencyType +
                ", frequency_basis=" + frequencyBasis +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
