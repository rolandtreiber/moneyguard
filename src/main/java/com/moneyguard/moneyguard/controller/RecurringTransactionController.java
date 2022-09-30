package com.moneyguard.moneyguard.controller;

import com.moneyguard.moneyguard.dao.RecurringTransactionDAO;
import com.moneyguard.moneyguard.repository.RecurringTransactionRepository;
import com.moneyguard.moneyguard.request.CreateRecurringTransactionRequest;
import com.moneyguard.moneyguard.request.UpdateRecurringTransactionRequest;
import com.moneyguard.moneyguard.response.DropdownListResource;
import com.moneyguard.moneyguard.response.MessageResponse;
import com.moneyguard.moneyguard.response.RecurringTransactionDetailResponse;
import com.moneyguard.moneyguard.service.RecurringTransactionService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("recurring-transactions")
@CrossOrigin
public class RecurringTransactionController {

    @Autowired
    RecurringTransactionService recurringTransactionService;

    @Autowired
    RecurringTransactionRepository recurringTransactionRepository;

    @PostMapping("")
    public RecurringTransactionDAO save(@Valid @RequestBody CreateRecurringTransactionRequest request) throws NotFoundException {
        return recurringTransactionService.create(request);
    }

    @PutMapping("/{id}")
    public RecurringTransactionDetailResponse update(@Valid @PathVariable UUID id, @RequestBody UpdateRecurringTransactionRequest request) throws NotFoundException {
        return recurringTransactionService.update(recurringTransactionRepository.findById(id), request);
    }

    @DeleteMapping("/{id}")
    public MessageResponse delete(@Valid @PathVariable UUID id) {
        if (recurringTransactionService.delete(recurringTransactionRepository.findById(id))) {
            return new MessageResponse("Deleted");
        }
        return new MessageResponse("Error. Please try again");
    }

    @GetMapping("/{id}")
    public RecurringTransactionDetailResponse view(@Valid @PathVariable UUID id) {
        return recurringTransactionService.get(recurringTransactionRepository.findById(id));
    }

    @GetMapping("")
    public List<RecurringTransactionDAO> list(@Valid @RequestParam(value = "page", defaultValue = "0") Integer page, @RequestParam(value = "search", defaultValue = "") String search) {
        return recurringTransactionService.list(page, search);
    }

    @GetMapping("/select")
    public List<DropdownListResource> list(@RequestParam(value = "search", defaultValue = "") String search) {
        return recurringTransactionService.getSelectData(search);
    }
}
