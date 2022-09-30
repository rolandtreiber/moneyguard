package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class CreateGuardRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Categories field is mandatory")
    private String[] categories;

    @NotNull(message = "Tags field is mandatory")
    private String[] tags;

    @NotNull(message = "Frequency is mandatory")
    private Short frequency;

    @NotNull(message = "Frequency basis is mandatory")
    private Integer frequencyBasis;

    @NotNull(message = "Threshold is mandatory")
    private Double threshold;

    @NotNull(message = "Start date is mandatory")
    private Date startDate;

    @NotNull(message = "Type is mandatory")
    private Short type;

    private Date endDate;

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
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

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
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
}
