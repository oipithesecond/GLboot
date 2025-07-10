package com.oipithesecond.glboot.mappers.impl;

import com.oipithesecond.glboot.domain.dto.GameDTO;
import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.mappers.GameMapper;
import com.oipithesecond.glboot.mappers.SessionMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GameMapperImpl implements GameMapper {

    private final SessionMapper sessionMapper;

    public GameMapperImpl(SessionMapper sessionMapper) {
        this.sessionMapper = sessionMapper;
    }

    @Override
    public Game fromDto(GameDTO gameDTO) {
        return new Game(
                gameDTO.id(),
                gameDTO.title(),
                gameDTO.description(),
                gameDTO.status(),
                Optional.ofNullable(gameDTO.sessions())
                        .map(session -> session.stream()
                                .map(sessionMapper::fromDto).toList()
                        ).orElse(null)
        );
    }

    @Override
    public GameDTO toDto(Game game) {
        return new  GameDTO(
                game.getId(),
                game.getTitle(),
                game.getDescription(),
                game.getStatus(),
                Optional.ofNullable(game.getSessions())
                        .map(sessions -> sessions.stream()
                                .map(sessionMapper::toDto)
                                .toList()
                        ).orElse(null)
        );
    }

}
