package com.oipithesecond.glboot.mappers;

import com.oipithesecond.glboot.domain.dto.CreateGameRequest;
import com.oipithesecond.glboot.domain.dto.GameDTO;
import com.oipithesecond.glboot.domain.entities.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameMapper {
    Game fromDto(GameDTO gameDTO);
    GameDTO toDto(Game game);
    Game toEntity(CreateGameRequest createGameRequest);
}
