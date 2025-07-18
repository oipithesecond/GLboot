package com.oipithesecond.glboot.domain.dto;

import java.util.List;
import java.util.UUID;

import com.oipithesecond.glboot.domain.SessionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GameDTO{
    private UUID id;
    private String title;
    private String description;
}

//        UUID id,
//        String title,
//        String description,
//        SessionStatus status,
//        List<SessionDTO> sessions
