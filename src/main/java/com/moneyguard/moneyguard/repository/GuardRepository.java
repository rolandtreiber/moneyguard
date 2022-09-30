package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.Guard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface GuardRepository extends JpaRepository<Guard, Long> {

    Set<Guard> findAllByUserId(UUID userId);
    List<Guard> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId);
    Page<Guard> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId, Pageable pageable);
    Guard findById(final UUID id);

}
