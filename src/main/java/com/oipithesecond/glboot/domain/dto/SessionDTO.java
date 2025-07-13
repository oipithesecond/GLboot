package com.oipithesecond.glboot.domain.dto;

import java.util.UUID;
import java.time.LocalDateTime;

public record SessionDTO(
        UUID id,
        UUID gameId,
        LocalDateTime startTime,
        LocalDateTime endTime) {

}
