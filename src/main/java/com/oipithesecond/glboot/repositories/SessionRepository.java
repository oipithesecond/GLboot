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
    Optional<Session> findByGame_IdAndAuthor_Id(UUID gameId, UUID authorId);
    List<Session> findByAuthor_Id(UUID userId);
    void deleteByGame_IdAndAuthor_Id(UUID gameId, UUID id);
}
