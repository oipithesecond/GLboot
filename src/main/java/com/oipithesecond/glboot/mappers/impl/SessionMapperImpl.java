package com.oipithesecond.glboot.mappers.impl;

import com.oipithesecond.glboot.domain.dto.SessionDTO;
import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.domain.entities.Session;
import com.oipithesecond.glboot.mappers.SessionMapper;
import org.springframework.stereotype.Component;

@Component
public class SessionMapperImpl implements SessionMapper {

    @Override
    public Session fromDto(SessionDTO sessionDTO) {
        Game game = new Game();
        game.setId(sessionDTO.gameId());

        return new Session(
                sessionDTO.id(),
                game,
                sessionDTO.startTime(),
                sessionDTO.endTime()
        );
    }

    @Override
    public SessionDTO toDto(Session session) {
        return new SessionDTO(
                session.getId(),
                session.getGame().getId(),
                session.getStartTime(),
                session.getEndTime()
        );
    }
}
