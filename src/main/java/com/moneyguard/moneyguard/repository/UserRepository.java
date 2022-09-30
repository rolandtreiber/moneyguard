package com.moneyguard.moneyguard.repository;

import com.moneyguard.moneyguard.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(final UUID id);
    Optional<User> findByName(String name);
    Optional<User> findByEmail(String email);
    Boolean existsByName(String name);
    Boolean existsByEmail(String email);
}
