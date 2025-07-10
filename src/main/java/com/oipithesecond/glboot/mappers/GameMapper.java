package com.oipithesecond.glboot.mappers;

import com.oipithesecond.glboot.domain.dto.GameDTO;
import com.oipithesecond.glboot.domain.entities.Game;

public interface GameMapper {
    Game fromDto(GameDTO gameDTO);
    GameDTO toDto(Game game);
}
