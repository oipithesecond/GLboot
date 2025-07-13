package com.oipithesecond.glboot.controllers;

import com.oipithesecond.glboot.domain.dto.SessionDTO;
import com.oipithesecond.glboot.domain.entities.Session;
import com.oipithesecond.glboot.mappers.SessionMapper;
import com.oipithesecond.glboot.repositories.SessionRepository;
import com.oipithesecond.glboot.services.GameService;
import com.oipithesecond.glboot.services.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path="/games/{game_id}/sessions")
public class SessionController {

    private final SessionService sessionService;
    private final SessionMapper sessionMapper;

    public SessionController(SessionService sessionService, SessionMapper sessionMapper) {
        this.sessionService = sessionService;
        this.sessionMapper = sessionMapper;
    }

    @GetMapping
    public List<SessionDTO> getSessionsbygameid(@PathVariable("game_id") UUID gameid) {
        return sessionService.getSessionsbygameid(gameid)
                .stream()
                .map(sessionMapper::toDto)
                .toList();
    }

    @PostMapping
    public SessionDTO createSession(
            @PathVariable("game_id") UUID gameid,
            @RequestBody SessionDTO sessiondto) {
        Session createdSession = sessionService.createSession(
                gameid,
                sessionMapper.fromDto(sessiondto)
        );
        return sessionMapper.toDto(createdSession);
    }
}
