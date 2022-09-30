package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.GuardDAO;
import com.moneyguard.moneyguard.model.Guard;
import com.moneyguard.moneyguard.request.CreateGuardRequest;
import com.moneyguard.moneyguard.request.UpdateGuardRequest;
import com.moneyguard.moneyguard.resource.Targetable;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.GuardDetailResponse;
import javassist.NotFoundException;

import java.util.List;

public interface GuardService {

    GuardDAO create(CreateGuardRequest request) throws NotFoundException;
    GuardDetailResponse update(Guard guard, UpdateGuardRequest request) throws NotFoundException;
    Boolean delete(Guard guard);
    GuardDetailResponse get(Guard guard);
    List<GuardDAO> list(Integer page, String search);
    List<DropdownListResource> getSelectData(String search);
    Targetable getCurrentState(Guard guard);

}
