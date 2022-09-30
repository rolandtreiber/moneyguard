package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.model.User;

public interface AuthService {

    User getAuthUser();
    void setAuthUser(User authUser);

}
