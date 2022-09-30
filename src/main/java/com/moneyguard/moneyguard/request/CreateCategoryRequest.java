package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateCategoryRequest {

    @NotBlank(message = "Name is mandatory")
    private String name;
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
}
