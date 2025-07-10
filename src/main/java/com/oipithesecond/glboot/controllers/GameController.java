package com.oipithesecond.glboot.controllers;

import com.oipithesecond.glboot.domain.dto.GameDTO;
import com.oipithesecond.glboot.domain.entities.Game;
import com.oipithesecond.glboot.mappers.GameMapper;
import com.oipithesecond.glboot.services.GameService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/games")
public class GameController {

    private final GameService gameService;
    private final GameMapper gameMapper;

    public GameController(GameService gameService, GameMapper gameMapper) {
        this.gameService = gameService;
        this.gameMapper = gameMapper;
    }

    @GetMapping
    public List<GameDTO> listGame() {
        return gameService.listGame()
                .stream()
                .map(gameMapper::toDto)
                .toList();
    }

    @PostMapping
    public GameDTO createGame(@RequestBody GameDTO gameDTO) {
        Game createdGame = gameService.createGame(
                gameMapper.fromDto(gameDTO));
        return gameMapper.toDto(createdGame);
    }

}
