package com.moneyguard.moneyguard;

import com.moneyguard.moneyguard.model.RecurringTransaction;
import com.moneyguard.moneyguard.repository.RecurringTransactionRepository;
import com.moneyguard.moneyguard.service.RecurringTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@EnableScheduling
@Configuration
@EnableAsync
@ConditionalOnProperty(name = "scheduler.enabled", matchIfMissing = true)
public class SchedulerConfig {

    @Autowired
    RecurringTransactionRepository recurringTransactionRepository;

    @Autowired
    RecurringTransactionService recurringTransactionService;

    @Scheduled(fixedRate = 36000000)
    @Async
    public void processAutomaticallyRecurringTransactions()
    {
        List<RecurringTransaction> recurringTransactions = recurringTransactionRepository.findAll();
        recurringTransactions.forEach(rc -> {
            recurringTransactionService.generateTransactions(rc);
        });
    }
}
