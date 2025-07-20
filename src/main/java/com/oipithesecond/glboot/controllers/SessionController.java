package com.oipithesecond.glboot.controllers;

import com.oipithesecond.glboot.domain.dto.CreateSessionRequest;
import com.oipithesecond.glboot.domain.dto.SessionDTO;
import com.oipithesecond.glboot.domain.entities.Session;
import com.oipithesecond.glboot.domain.entities.User;
import com.oipithesecond.glboot.mappers.SessionMapper;
import com.oipithesecond.glboot.repositories.SessionRepository;
import com.oipithesecond.glboot.services.GameService;
import com.oipithesecond.glboot.services.SessionService;
import com.oipithesecond.glboot.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;
    private final SessionMapper sessionMapper;
    private final GameService gameService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<SessionDTO>> findSessions(
            @RequestParam(name = "gameId", required = false) UUID gameId,
            @RequestParam(name = "userId", required = false) UUID userId) {

        List<Session> sessions;

        if (gameId != null && userId != null) {
            sessions = sessionService.getSessionByUserAndGame(userId, gameId)
                    .map(List::of)
                    .orElse(Collections.emptyList());
        } else if (gameId != null) {
            sessions = sessionService.getSessionsByGame(gameId);
        } else if (userId != null) {
            sessions = sessionService.getSessionsByUser(userId);
        } else {
            sessions = Collections.emptyList();
        }

        List<SessionDTO> sessionDTOs = sessions.stream()
                .map(sessionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(sessionDTOs);
    }


    @PostMapping
    public ResponseEntity<SessionDTO> createSession(
            @RequestBody CreateSessionRequest request,
            @AuthenticationPrincipal User user) {

        Session createdSession = sessionService.createSession(request.getGameId(), user.getId());
        SessionDTO sessionDTO = sessionMapper.toDto(createdSession);
        return new ResponseEntity<>(sessionDTO, HttpStatus.CREATED);
    }

    @GetMapping("/{sessionId}")
    public ResponseEntity<SessionDTO> getSessionById(@PathVariable("sessionId") UUID sessionId) {
        // We assume a method getSessionById(sessionId) exists in your SessionService
        return sessionService.getSessionById(sessionId)
                .map(sessionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{sessionId}/end")
    public ResponseEntity<SessionDTO> endSession(
            @PathVariable("sessionId") UUID sessionId,
            @AuthenticationPrincipal User user) {

        try {
            return sessionService.endSession(sessionId, user.getId())
                    .map(sessionMapper::toDto)
                    .map(ResponseEntity::ok)
                    .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        } catch (SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/{sessionId}")
    public ResponseEntity<Void> deleteSession(
            @PathVariable("sessionId") UUID sessionId,
            @AuthenticationPrincipal User user) {
        try {
            sessionService.deleteSession(sessionId, user.getId());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (SecurityException e) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        } catch (IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
