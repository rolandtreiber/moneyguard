package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.model.User;
import org.springframework.stereotype.Service;

@Service("authService")
public class AuthServiceImpl implements AuthService {

    private User authUser;

    @Override
    public User getAuthUser() {
        return authUser;
    }

    @Override
    public void setAuthUser(User authUser) {
        this.authUser = authUser;
    }
}
