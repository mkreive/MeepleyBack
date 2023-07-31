package lt.monikos.meepley.controller;

import lt.monikos.meepley.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

//    @GetMapping("/findByTitle")
//    public List<Game> findByTitle(String gameTitle) {
//        return gameService.getByTitle(gameTitle);
//    }
//
//    @GetMapping("/findByCategory")
//    public List<Game> findByCategory(String categories) {
//        return gameService.getByCategory(categories);
//    }
//
//    @GetMapping("/findByComplexity")
//    public List<Game> findByComplexity(String complexities) {
//        return gameService.getByComplexity(complexities);
//    }


}
