package com.oipithesecond.glboot.domain.dto;

import com.oipithesecond.glboot.domain.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionDTO{
    private UUID id;
    private GameDTO game;
    private AuthorDTO author;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private SessionStatus status;
}