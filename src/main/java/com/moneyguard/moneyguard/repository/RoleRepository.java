package com.moneyguard.moneyguard.repository;
import com.moneyguard.moneyguard.model.ERole;
import com.moneyguard.moneyguard.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}