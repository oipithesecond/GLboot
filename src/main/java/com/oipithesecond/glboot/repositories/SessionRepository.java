package com.oipithesecond.glboot.repositories;

import com.oipithesecond.glboot.domain.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SessionRepository extends JpaRepository<Session, UUID> {
    List<Session> findByGame_Id(UUID gameId);
    Optional<Session> findByGame_IdAndId(UUID gameId, UUID Id);
    void deleteByGame_IdAndId(UUID gameId, UUID id);
}
