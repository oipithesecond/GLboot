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

    @GetMapping("/{gameId}")
    public ResponseEntity<GameDTO> getGameById(@PathVariable("gameId") UUID gameId) {
        Game game = gameService.getGameById(gameId);
        GameDTO gameDTO = gameMapper.toDto(game);
        return ResponseEntity.ok(gameDTO);
    }

    @PutMapping("/{gameId}")
    public ResponseEntity<GameDTO> updateGame(
            @PathVariable("gameId") UUID gameId,
            @RequestBody GameDTO gameDTO) {
        Game gameToUpdate = gameMapper.fromDto(gameDTO);
        Game updatedGame = gameService.updateGame(gameId, gameToUpdate);
        return ResponseEntity.ok(gameMapper.toDto(updatedGame));
    }

    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable("gameId") UUID gameId) {
        gameService.deleteGame(gameId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
