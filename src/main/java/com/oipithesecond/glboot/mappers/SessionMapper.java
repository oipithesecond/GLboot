package com.oipithesecond.glboot.mappers;

import com.oipithesecond.glboot.domain.dto.SessionDTO;
import com.oipithesecond.glboot.domain.entities.Session;

public interface SessionMapper {

    Session fromDto(SessionDTO sessionDTO);
    SessionDTO toDto(Session session);
}
