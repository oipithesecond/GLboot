package com.oipithesecond.glboot.mappers;

import com.oipithesecond.glboot.domain.dto.SessionDTO;
import com.oipithesecond.glboot.domain.entities.Session;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SessionMapper {
    @Mapping(target = "author", source = "author")
    Session fromDto(SessionDTO sessionDTO);
    @Mapping(target = "author", source = "author")
    @Mapping(target = "game", source = "game")
    SessionDTO toDto(Session session);
}
