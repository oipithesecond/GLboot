package com.oipithesecond.glboot.services;

import com.oipithesecond.glboot.domain.entities.Game;

import java.util.List;
import java.util.UUID;

public interface GameService {
    List<Game> listGame();
    Game createGame(Game game);
    Game getGameById(UUID id);
    Game updateGame(UUID id, Game game);
    void deleteGame(UUID id);
}
