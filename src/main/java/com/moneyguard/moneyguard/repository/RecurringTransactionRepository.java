package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.RecurringTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecurringTransactionRepository extends JpaRepository<RecurringTransaction, Long> {

    List<RecurringTransaction> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId);
    Page<RecurringTransaction> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId, Pageable pageable);
    RecurringTransaction findById(final UUID id);
}
