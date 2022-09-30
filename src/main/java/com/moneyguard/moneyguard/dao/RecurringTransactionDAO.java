package com.moneyguard.moneyguard.dao;

import java.util.Date;
import java.util.UUID;

public class RecurringTransactionDAO {

    private UUID id;
    private String name;
    private Date startDate;
    private Date endDate;
    private Short frequencyType;
    private Integer frequencyBasis;
    private Float amount;
    private Short type;
    private Date createdAt;
    private Date updatedAt;

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
}
