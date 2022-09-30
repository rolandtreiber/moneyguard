package com.moneyguard.moneyguard.response;

import com.moneyguard.moneyguard.model.User;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.UUID;

public class UserDetailResponse {

    private UUID id;
    private String prefix;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private String currencySymbol;
    private Boolean currencyPlacement;
    private Date createdAt;
    private Date updatedAt;

    public UserDetailResponse(User user) {
        BeanUtils.copyProperties(user, this);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }
    
    public Boolean getCurrencyPlacement() {
        return currencyPlacement;
    }

    public void setCurrencyPlacement(Boolean currencyPlacement) {
        this.currencyPlacement = currencyPlacement;
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
