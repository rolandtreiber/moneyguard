package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.Template;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    List<Template> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId);
    Page<Template> findAllByNameContainsIgnoreCaseAndUserIdOrderByNameAsc(String name, UUID userId, Pageable pageable);
    Template findById(final UUID id);

}
