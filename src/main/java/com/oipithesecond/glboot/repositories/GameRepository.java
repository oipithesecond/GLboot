package com.oipithesecond.glboot.repositories;

import com.oipithesecond.glboot.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRepository extends JpaRepository<Game, UUID> {

    boolean existsByNameIgnoreCase(String name);
}
