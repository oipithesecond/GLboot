package com.oipithesecond.glboot.services.impl;

import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.repositories.GameRepository;
import com.oipithesecond.glboot.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

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

    @Override
    public Optional<Game> getGame(UUID id) {
        return gameRepository.findById(id);
    }

    @Transactional
    @Override
    public Game updateGame(UUID id, Game game) {
        if(null == game.getId()) {
            throw new IllegalArgumentException("Game id is required");
        }
        if(!Objects.equals(game.getId(), id)) {
            throw new IllegalArgumentException("Game id does not match");
        }

        Game existingGame = gameRepository.findById(id).orElseThrow(()->
                new IllegalArgumentException("Game id does not exist"));

        existingGame.setTitle(game.getTitle());
        existingGame.setDescription(game.getDescription());
        existingGame.setStatus(game.getStatus());

        return gameRepository.save(existingGame);
    }

    @Override
    public void deleteGame(UUID id) {
        gameRepository.deleteById(id);
    }
}
