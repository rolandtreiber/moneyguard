package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAll();
    List<Category> findAllByNameContainsIgnoreCaseAndUserIdAndTypeOrderByNameAsc(String name, UUID userId, Short type);
    Page<Category> findAllByNameContainsIgnoreCaseAndUserIdAndTypeOrderByNameAsc(String name, UUID userId, Pageable pageable, Short type);
    Category findById(final UUID id);

}
