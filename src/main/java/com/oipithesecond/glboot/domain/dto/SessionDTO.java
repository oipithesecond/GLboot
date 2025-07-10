package com.oipithesecond.glboot.domain.dto;

import java.util.UUID;
import java.time.LocalDateTime;
import com.oipithesecond.glboot.domain.entities.Game;

public record SessionDTO(
        UUID id,
        Game game,
        LocalDateTime startTime,
        LocalDateTime endTime) {

}
