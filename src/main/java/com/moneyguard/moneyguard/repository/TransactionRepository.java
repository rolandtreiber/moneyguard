package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>, TransactionAdvancedSearchRepository {

    List<Transaction> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId);
    Transaction findById(final UUID id);

}
