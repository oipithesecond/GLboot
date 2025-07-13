package com.oipithesecond.glboot.services;

import com.oipithesecond.glboot.domain.entities.Session;

import java.util.List;
import java.util.UUID;

public interface SessionService {
    List<Session> getSessionsbygameid(UUID gameid);
    Session createSession(UUID gameid, Session session);
}
