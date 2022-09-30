package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.SavingGoal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface SavingGoalRepository extends JpaRepository<SavingGoal, Long> {

    Set<SavingGoal> findAllByUserId(UUID userId);
    List<SavingGoal> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId);
    Page<SavingGoal> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId, Pageable pageable);
    SavingGoal findById(final UUID id);

}
