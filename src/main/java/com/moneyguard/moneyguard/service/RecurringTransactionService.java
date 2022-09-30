package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.RecurringTransactionDAO;
import com.moneyguard.moneyguard.model.RecurringTransaction;
import com.moneyguard.moneyguard.request.CreateRecurringTransactionRequest;
import com.moneyguard.moneyguard.request.UpdateRecurringTransactionRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.RecurringTransactionDetailResponse;
import javassist.NotFoundException;

import java.util.List;

public interface RecurringTransactionService {

    RecurringTransactionDAO create(CreateRecurringTransactionRequest request) throws NotFoundException;
    RecurringTransactionDetailResponse update(RecurringTransaction recurringTransaction, UpdateRecurringTransactionRequest request) throws NotFoundException;
    Boolean delete(RecurringTransaction recurringTransaction);
    RecurringTransactionDetailResponse get(RecurringTransaction recurringTransaction);
    List<RecurringTransactionDAO> list(Integer page, String search);
    List<DropdownListResource> getSelectData(String search);

}
