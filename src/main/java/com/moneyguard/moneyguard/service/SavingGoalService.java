package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.SavingGoalDAO;
import com.moneyguard.moneyguard.model.SavingGoal;
import com.moneyguard.moneyguard.request.CreateSavingGoalRequest;
import com.moneyguard.moneyguard.request.UpdateSavingGoalRequest;
import com.moneyguard.moneyguard.resource.Targetable;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.SavingGoalDetailResponse;

import java.util.List;

public interface SavingGoalService {

    SavingGoalDAO create(CreateSavingGoalRequest request);
    SavingGoalDetailResponse update(SavingGoal savingGoal, UpdateSavingGoalRequest request);
    Boolean delete(SavingGoal savingGoal);
    SavingGoalDetailResponse get(SavingGoal savingGoal);
    List<SavingGoalDAO> list(Integer page, String search);
    List<DropdownListResource> getSelectData(String search);
    Targetable getCurrentState(SavingGoal savingGoal);
}
