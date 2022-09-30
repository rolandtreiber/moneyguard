package com.moneyguard.moneyguard.request;

public class UpdateImportanceLevelRequest {

    private String name;
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
