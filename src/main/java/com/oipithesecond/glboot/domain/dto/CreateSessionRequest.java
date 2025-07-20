package com.oipithesecond.glboot.domain.dto;

import com.oipithesecond.glboot.domain.SessionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSessionRequest {
    @NotNull(message = "gameId is required")
    private UUID gameId;

    @NotNull(message = "status is required")
    private SessionStatus sessionStatus;
}
