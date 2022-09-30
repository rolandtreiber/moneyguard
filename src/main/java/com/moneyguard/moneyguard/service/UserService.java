package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.model.ImportanceLevel;
import com.moneyguard.moneyguard.model.User;
import com.moneyguard.moneyguard.request.DashboardRequest;
import com.moneyguard.moneyguard.request.UpdateAccountRequest;
import com.moneyguard.moneyguard.response.DashboardResponse;
import com.moneyguard.moneyguard.response.UserDetailResponse;

import java.text.ParseException;
import java.util.Set;

public interface UserService {

    Set<ImportanceLevel> generateDefaultImportanceLevels(User user);
    DashboardResponse getDashboard(User user, DashboardRequest request) throws ParseException;
    UserDetailResponse updateAccount(UpdateAccountRequest request, User user);

}
