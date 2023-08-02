package lt.monikos.meepley.controller;

import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.service.GameService;
import lt.monikos.meepley.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/games")
public class GameController {

    private GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    // Game loans by user
    @GetMapping("/secure/ischeckedout/byuser")
    public Boolean checkoutGameByUser(@RequestHeader(value = "Authorization") String token,
                                      @RequestParam Long gameId) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return gameService.checkoutGameByUser(userEmail, gameId);
    }
    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return gameService.currentLoansCount(userEmail);
    }
    @PutMapping("/secure/checkout")
    public Game checkoutGame(@RequestHeader(value = "Authorization") String token, @RequestParam Long gameId) throws Exception {
        String userEmail = ExtractJWT.payloadJWTExtraction(token, "\"sub\"");
        return gameService.checkoutGame(userEmail, gameId);
    }




}
