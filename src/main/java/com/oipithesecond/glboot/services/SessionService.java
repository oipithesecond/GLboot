package com.oipithesecond.glboot.services;

import com.oipithesecond.glboot.domain.entities.Session;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SessionService {
    List<Session> getSessionsbygameid(UUID gameid);
    Session createSession(UUID gameid, Session session);
    Optional<Session> getSession(UUID gameid, UUID id);
    Session updateSession(UUID gameid, UUID id, Session session);
}
