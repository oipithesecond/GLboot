package com.oipithesecond.glboot.services;

import com.oipithesecond.glboot.domain.entities.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionService {
    Session createSession(UUID gameId, UUID userId);
    Optional<Session> getSessionByUserAndGame(UUID userId, UUID gameId);
    List<Session> getSessionsByGame(UUID gameId);
    List<Session> getSessionsByUser(UUID userId);
    Optional<Session> getSessionById(UUID sessionId);
    Optional<Session> endSession(UUID sessionId, UUID userId);
    void deleteSession(UUID sessionId, UUID userId);
}
