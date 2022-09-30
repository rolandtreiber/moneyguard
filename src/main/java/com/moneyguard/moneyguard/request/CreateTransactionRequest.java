package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateTransactionRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Categories field is mandatory")
    private String[] categories;

    @NotNull(message = "Tags field is mandatory")
    private String[] tags;

    private String importanceLevel;

    @NotNull(message = "Amount is mandatory")
    private Float amount;

    @NotNull(message = "Type is mandatory")
    private Short type;

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

    public String getImportanceLevel() {
        return importanceLevel;
    }

    public void setImportanceLevel(String importanceLevel) {
        this.importanceLevel = importanceLevel;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }
}
