package com.oipithesecond.glboot.controllers;

import com.oipithesecond.glboot.domain.dto.CreateGameRequest;
import com.oipithesecond.glboot.domain.dto.GameDTO;
import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.mappers.GameMapper;
import com.oipithesecond.glboot.services.GameService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    @GetMapping
    public ResponseEntity<List<GameDTO>> listGame() {
        List<GameDTO> games = gameService.listGame()
                .stream()
                .map(gameMapper::toDto)
                .toList();
        return ResponseEntity.ok(games);
    }

    @PostMapping
    public ResponseEntity<GameDTO> createGame(
            @Valid @RequestBody CreateGameRequest createGameRequest) {
        Game gameToCreate = gameMapper.toEntity(createGameRequest);
        Game game = gameService.createGame(gameToCreate);
        return new ResponseEntity<>(
                gameMapper.toDto(game),
                HttpStatus.CREATED
        );
    }

    @GetMapping(path="/{game_id}")
    public Optional<GameDTO> getGame(@PathVariable("game_id") UUID gameId) {
        return gameService.getGame(gameId)
                .map(gameMapper::toDto);
    }

    @PutMapping(path="/{game_id}")
    public GameDTO updateGame(@PathVariable("game_id") UUID gameId, @RequestBody GameDTO gameDTO) {
        Game updateGame = gameService.updateGame(
                gameId,
                gameMapper.fromDto(gameDTO));

        return gameMapper.toDto(updateGame);
    }

    @DeleteMapping(path="/{game_id}")
    public void deleteGame(@PathVariable("game_id") UUID gameId) {
        gameService.deleteGame(gameId);
    }

}
