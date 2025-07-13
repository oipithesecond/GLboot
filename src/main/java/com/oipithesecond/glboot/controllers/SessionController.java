package com.oipithesecond.glboot.controllers;

import com.oipithesecond.glboot.domain.dto.SessionDTO;
import com.oipithesecond.glboot.domain.entities.Session;
import com.oipithesecond.glboot.mappers.SessionMapper;
import com.oipithesecond.glboot.repositories.SessionRepository;
import com.oipithesecond.glboot.services.GameService;
import com.oipithesecond.glboot.services.SessionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
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

    @GetMapping(path="/{session_id}")
    public Optional<SessionDTO> getSessionbyid(
            @PathVariable("game_id") UUID gameid,
            @PathVariable("session_id") UUID sessionid
    ) {
        return sessionService.getSession(gameid, sessionid).map(sessionMapper::toDto);
    }

    @PutMapping(path="/{session_id}")
    public SessionDTO updateSession(
            @PathVariable("game_id") UUID gameid,
            @PathVariable("session_id") UUID sessionid,
            @RequestBody SessionDTO sessionDto
    ){
        Session updatedSession = sessionService.updateSession(
                gameid,
                sessionid,
                sessionMapper.fromDto(sessionDto)
        );
        return sessionMapper.toDto(updatedSession);
    }

    @DeleteMapping(path="/{session_id}")
    public void deleteSession(
            @PathVariable("game_id") UUID gameid,
            @PathVariable("session_id") UUID sessionid
    ){
        sessionService.deleteSession(gameid, sessionid);
    }

}
