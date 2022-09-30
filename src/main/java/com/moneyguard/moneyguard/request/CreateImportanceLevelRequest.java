package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateImportanceLevelRequest {


    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotNull(message = "Level is mandatory")
    private Short level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getLevel() {
        return level;
    }

    public void setLevel(Short level) {
        this.level = level;
    }
}
