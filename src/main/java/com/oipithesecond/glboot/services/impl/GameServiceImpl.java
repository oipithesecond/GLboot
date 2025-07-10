package com.oipithesecond.glboot.services.impl;

import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.repositories.GameRepository;
import com.oipithesecond.glboot.services.GameService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public List<Game> listGame() {
        return gameRepository.findAll();
    }

    @Override
    public Game createGame(Game game) {
        if(null != game.getId()) {
            throw new IllegalArgumentException("Game already exists");
        }
        if(null == game.getTitle() || game.getTitle().isBlank()) {
            throw new IllegalArgumentException("Game title is required");
        }

        return gameRepository.save(new Game(
                null,
                game.getTitle(),
                game.getDescription(),
                null,
                null
        ));
    }
}
