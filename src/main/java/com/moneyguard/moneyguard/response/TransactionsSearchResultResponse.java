package com.moneyguard.moneyguard.response;

import java.util.ArrayList;
import java.util.List;

public class TransactionsSearchResultResponse {

    private List<TransactionListResponse> list = new ArrayList<>();
    private Double total = Double.valueOf(0);

    public List<TransactionListResponse> getList() {
        return list;
    }

    public void setList(List<TransactionListResponse> list) {
        this.list = list;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
