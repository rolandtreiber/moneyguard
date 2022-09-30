package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAll();
    List<Tag> findAllByNameContainsIgnoreCaseAndUserIdAndTypeOrderByNameAsc(String name, UUID userId, Short type);
    Page<Tag> findAllByNameContainsIgnoreCaseAndUserIdAndTypeOrderByNameAsc(String name, UUID userId, Pageable pageable, Short type);
    Tag findById(final UUID id);

}
