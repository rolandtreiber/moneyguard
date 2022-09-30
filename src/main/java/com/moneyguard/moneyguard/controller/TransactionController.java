package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.dao.TransactionDAO;
import com.moneyguard.moneyguard.repository.TransactionRepository;
import com.moneyguard.moneyguard.request.CreateTransactionRequest;
import com.moneyguard.moneyguard.request.RetrieveTransactionsRequest;
import com.moneyguard.moneyguard.request.UpdateTransactionRequest;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.response.TransactionDetailResponse;
import com.moneyguard.moneyguard.response.TransactionsSearchResultResponse;
import com.moneyguard.moneyguard.service.TransactionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("transactions")
@CrossOrigin
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @Autowired
    TransactionRepository transactionRepository;

    @PostMapping("")
    public TransactionDAO save(@Valid @RequestBody CreateTransactionRequest request) throws NotFoundException {
        return transactionService.create(request);
    }

    @PutMapping("/{id}")
    public TransactionDetailResponse update(@Valid @PathVariable UUID id, @RequestBody UpdateTransactionRequest request) throws NotFoundException {
        return transactionService.update(transactionRepository.findById(id), request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@Valid @PathVariable UUID id) {
        if (transactionService.delete(transactionRepository.findById(id))) {
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Error. Please try again");
    }

    @GetMapping("/{id}")
    public TransactionDetailResponse view(@Valid @PathVariable UUID id) {
        return transactionService.get(transactionRepository.findById(id));
    }

    @PostMapping("/list")
    public TransactionsSearchResultResponse list(@Valid @RequestBody RetrieveTransactionsRequest request) {
        return transactionService.list(request);
    }
}
