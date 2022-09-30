package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotBlank;

public class UpdateCategoryRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
