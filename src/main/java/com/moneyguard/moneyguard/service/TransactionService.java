package com.moneyguard.moneyguard.service;

import com.moneyguard.moneyguard.dao.TransactionDAO;
import com.moneyguard.moneyguard.model.Transaction;
import com.moneyguard.moneyguard.request.CreateTransactionRequest;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import com.moneyguard.moneyguard.request.UpdateTransactionRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.TransactionDetailResponse;
import com.moneyguard.moneyguard.response.TransactionsSearchResultResponse;
import javassist.NotFoundException;

import java.util.List;

public interface TransactionService {

    TransactionDAO create(CreateTransactionRequest request) throws NotFoundException;
    TransactionDetailResponse update(Transaction transaction, UpdateTransactionRequest request) throws NotFoundException;
    Boolean delete(Transaction transaction);
    TransactionDetailResponse get(Transaction transaction);
    TransactionsSearchResultResponse list(RetrieveTransactionsRequest request);
    List<DropdownListResource> getSelectData(String search);

}
