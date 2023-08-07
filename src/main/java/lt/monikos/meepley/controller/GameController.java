package lt.monikos.meepley.controller;

import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.entity.Token;
import lt.monikos.meepley.responseModels.AccountReservations;
import lt.monikos.meepley.service.GameService;
import lt.monikos.meepley.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        Token extracted = ExtractJWT.payloadJWTExtraction(token);
        return gameService.checkoutGameByUser(extracted.getEmail(), gameId);
    }

    @GetMapping("/secure/currentloans")
    public List<AccountReservations> currentReservations(@RequestHeader(value = "Authorization") String token) throws Exception {
        Token extracted = ExtractJWT.payloadJWTExtraction(token);
        return gameService.currentReservations(extracted.getEmail());
    }

    @GetMapping("/secure/currentloans/count")
    public int currentLoansCount(@RequestHeader(value = "Authorization") String token) {
        Token extracted = ExtractJWT.payloadJWTExtraction(token);
        return gameService.currentLoansCount(extracted.getEmail());
    }

    @PutMapping("/secure/return")
    public void returnGame(@RequestHeader(value = "Authorization") String token, @RequestParam Long gameId) throws Exception {
        Token extracted = ExtractJWT.payloadJWTExtraction(token);
        gameService.returnGame(extracted.getEmail(), gameId);
    }

    @PutMapping("/secure/renew/loan")
    public void renewLoan(@RequestHeader(value = "Authorization") String token, @RequestParam Long gameId) throws Exception {
        Token extracted = ExtractJWT.payloadJWTExtraction(token);
        gameService.renewLoan(extracted.getEmail(), gameId);
    }


    @PutMapping("/secure/checkout")
    public Game checkoutGame(@RequestHeader(value = "Authorization") String token, @RequestParam Long gameId) throws Exception {
        Token extracted = ExtractJWT.payloadJWTExtraction(token);
        return gameService.checkoutGame(extracted.getEmail(), gameId);
    }






}
