package com.oipithesecond.glboot.services.impl;

import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.domain.entities.Session;
import com.oipithesecond.glboot.domain.SessionStatus;
import com.oipithesecond.glboot.domain.entities.User;
import com.oipithesecond.glboot.repositories.GameRepository;
import com.oipithesecond.glboot.repositories.SessionRepository;
import com.oipithesecond.glboot.repositories.UserRepository;
import com.oipithesecond.glboot.services.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public Session createSession(UUID gameId, UUID userId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new IllegalStateException("Game with id " + gameId + " not found"));

        User author = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalStateException("User with id " + userId + " not found"));

        Session sessionToSave = Session.builder()
                .game(game)
                .author(author)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .status(SessionStatus.INGAME)
                .SessionLength(Duration.ZERO)
                .build();

        return sessionRepository.save(sessionToSave);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Session> getSessionByUserAndGame(UUID userId, UUID gameId) {
        // CORRECTED: Changed from findByGameIdAndUserId to findByGame_IdAndAuthor_Id
        return sessionRepository.findByGame_IdAndAuthor_Id(gameId, userId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Session> getSessionsByGame(UUID gameId) {
        // CORRECTED: Changed from findByGameId to findByGame_Id
        return sessionRepository.findByGame_Id(gameId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Session> getSessionsByUser(UUID userId) {
        // CORRECTED: Changed from findByUserId to findByAuthor_Id
        return sessionRepository.findByAuthor_Id(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Session> getSessionById(UUID sessionId) {
        return sessionRepository.findById(sessionId);
    }

    @Override
    @Transactional
    public Optional<Session> endSession(UUID sessionId, UUID userId) {
        return sessionRepository.findById(sessionId)
                .map(session -> {
                    if (!session.getAuthor().getId().equals(userId)) {
                        throw new SecurityException("User is not authorized to end this session.");
                    }
                    if (session.getStatus() == SessionStatus.COMPLETED) {
                        throw new IllegalStateException("Session is already completed.");
                    }
                    session.setStatus(SessionStatus.COMPLETED);
                    return sessionRepository.save(session);
                });
    }

    @Override
    @Transactional
    public void deleteSession(UUID sessionId, UUID userId) {
        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalStateException("Session with id " + sessionId + " not found."));

        if (!session.getAuthor().getId().equals(userId)) {
            throw new SecurityException("User is not authorized to delete this session.");
        }

        sessionRepository.delete(session);
    }
}