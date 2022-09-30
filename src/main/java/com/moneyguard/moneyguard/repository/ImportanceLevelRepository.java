package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.ImportanceLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ImportanceLevelRepository extends JpaRepository<ImportanceLevel, Long> {

    List<ImportanceLevel> findAllByNameContainsIgnoreCaseAndUserIdOrderByLevelDesc(String name, UUID userId);
    Page<ImportanceLevel> findAllByNameContainsIgnoreCaseAndUserIdOrderByLevelDesc(String name, UUID userId, Pageable pageable);
    ImportanceLevel findById(final UUID id);

}
