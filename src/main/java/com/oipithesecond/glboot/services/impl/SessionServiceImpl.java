package com.oipithesecond.glboot.services.impl;

import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.domain.entities.Session;
import com.oipithesecond.glboot.repositories.GameRepository;
import com.oipithesecond.glboot.repositories.SessionRepository;
import com.oipithesecond.glboot.services.SessionService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final GameRepository gameRepository;

    public SessionServiceImpl(SessionRepository sessionRepository, GameRepository gameRepository) {
        this.sessionRepository = sessionRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Session> getSessionsbygameid(UUID gameid) {
        return sessionRepository.findByGame_Id(gameid);
    }

    @Override
    public Session createSession(UUID gameid, Session session) {
        if(null!= session.getId()){
            throw new IllegalStateException("session already exists");
        }

        Game game = gameRepository.findById(gameid)
                .orElseThrow(() -> new IllegalStateException("game not found"));

        LocalDateTime  now = LocalDateTime.now();
        Session sessionToSave = new Session(
                null,
                game,
                now,
                now
        );

        return sessionRepository.save(sessionToSave);
    }
}
