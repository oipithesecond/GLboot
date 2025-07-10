package com.oipithesecond.glboot.domain.dto;

import java.util.List;
import java.util.UUID;

import com.oipithesecond.glboot.domain.entities.GameStatus;

public record GameDTO(
        UUID id,
        String title,
        String description,
        GameStatus status,
        List<SessionDTO> sessions
) {

}
