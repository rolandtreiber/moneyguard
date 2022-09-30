package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotBlank;

public class UpdatePasswordRequest {

    @NotBlank(message = "Password is mandatory")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
