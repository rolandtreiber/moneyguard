package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.Transaction;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;

import java.util.List;

public interface TransactionAdvancedSearchRepository {

    List<Transaction> advancedSearch(RetrieveTransactionsRequest request);
    Double getTotal(RetrieveTransactionsRequest request);
}
