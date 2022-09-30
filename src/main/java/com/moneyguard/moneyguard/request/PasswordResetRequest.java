package com.moneyguard.moneyguard.request;

import javax.validation.constraints.NotBlank;

public class PasswordResetRequest {

    @NotBlank(message = "Email is mandatory")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
