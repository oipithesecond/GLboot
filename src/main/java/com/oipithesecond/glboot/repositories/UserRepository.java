package com.oipithesecond.glboot.repositories;

import com.oipithesecond.glboot.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
