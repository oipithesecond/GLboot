package com.oipithesecond.glboot.services.impl;

import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.repositories.GameRepository;
import com.oipithesecond.glboot.services.GameService;
import jakarta.persistence.EntityNotFoundException;
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
    @Transactional
    public Game createGame(Game game) {
        if(null != game.getId()) {
            throw new IllegalArgumentException("Game already exists");
        }
        if(null == game.getTitle() || game.getTitle().isBlank()) {
            throw new IllegalArgumentException("Game title is required");
        }

        if(gameRepository.existsByTitleIgnoreCase(game.getTitle())){
            throw new IllegalArgumentException("Game with same name already exists");
        }

        return gameRepository.save(game);
    }

    @Override
    public Game getGameById(UUID id) {
        return gameRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Game with id " + id + " not found"));
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

        return gameRepository.save(existingGame);
    }

    @Override
    public void deleteGame(UUID id) {
        Optional<Game> game = gameRepository.findById(id);
        if(game.isPresent()){
            if(!game.get().getSessions().isEmpty()){
                throw new IllegalStateException("game has sessions associated with it");
            }
            gameRepository.deleteById(id);
        }
    }
}
