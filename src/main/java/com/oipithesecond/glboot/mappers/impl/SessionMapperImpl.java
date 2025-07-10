package com.oipithesecond.glboot.mappers.impl;

import com.oipithesecond.glboot.domain.dto.SessionDTO;
import com.oipithesecond.glboot.domain.entities.Session;
import com.oipithesecond.glboot.mappers.SessionMapper;
import org.springframework.stereotype.Component;

@Component
public class SessionMapperImpl implements SessionMapper {
    @Override
    public Session fromDto(SessionDTO sessionDTO) {
        return new Session(
               sessionDTO.id(),
                sessionDTO.game(),
                sessionDTO.startTime(),
                sessionDTO.endTime()
        );
    }

    @Override
    public SessionDTO toDto(Session session) {
        return new  SessionDTO(
                session.getId(),
                session.getGame(),
                session.getStartTime(),
                session.getEndTime()
        );
    }
}
