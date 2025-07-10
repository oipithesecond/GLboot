package com.oipithesecond.glboot.domain.dto;

public record ErrorResponse(
        int status,
        String message,
        String details
) {
}
