package com.moneyguard.moneyguard.response;

public class GenericSuccessResponse {

    public GenericSuccessResponse() {
        this.message = "Success";
    }

    public GenericSuccessResponse(String message) {
        this.message = message;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
