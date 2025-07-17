package com.oipithesecond.glboot.domain.dto;

import java.util.List;
import java.util.UUID;

import com.oipithesecond.glboot.domain.SessionStatus;

public record GameDTO(
        UUID id,
        String title,
        String description,
        SessionStatus status,
        List<SessionDTO> sessions
) {

}
