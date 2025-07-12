package com.oipithesecond.glboot.services;

import com.oipithesecond.glboot.domain.entities.Game;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GameService {
    List<Game> listGame();
    Game createGame(Game game);
    Optional<Game> getGame(UUID id);
}
