package lt.monikos.meepley.controller;

import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/findByTitle")
    public List<Game> findByTitle(String gameTitle) {
        return gameService.getByTitle(gameTitle);
    }

    @GetMapping("/findByTitle")
    public List<Game> findByCategory(String gameCategory) {
        return gameService.getByCategory(gameCategory);
    }
}
