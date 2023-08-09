package lt.monikos.meepley.controller;

import lt.monikos.meepley.entity.Token;
import lt.monikos.meepley.repository.GameRepository;
import lt.monikos.meepley.requestModels.AddGameRequest;
import lt.monikos.meepley.service.AdminService;
import lt.monikos.meepley.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService, GameRepository gameRepository) {
        this.adminService = adminService;
    }

    @PostMapping("/secure/add/game")
    public void postGame(@RequestHeader(value="Authorization") String token,
                         @RequestBody AddGameRequest addGameRequest) throws Exception {
        Token extraction = ExtractJWT.payloadJWTExtraction(token);
        String admin = extraction.getUserType();
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.postGame(addGameRequest);
    }

    @PutMapping("/secure/increase/game/quantity")
    public void increaseGameQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long gameId) throws Exception {
        Token extraction = ExtractJWT.payloadJWTExtraction(token);
        String admin = extraction.getUserType();
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.increaseGameQuantity(gameId);
    }

    @PutMapping("/secure/decrease/game/quantity")
    public void decreaseGameQuantity(@RequestHeader(value="Authorization") String token,
                                     @RequestParam Long gameId) throws Exception {
        Token extraction = ExtractJWT.payloadJWTExtraction(token);
        String admin = extraction.getUserType();
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.decreaseGameQuantity(gameId);
    }

    @DeleteMapping("/secure/delete/game")
    public void deleteGame(@RequestHeader(value="Authorization") String token,
                           @RequestParam Long gameId) throws Exception {
        Token extraction = ExtractJWT.payloadJWTExtraction(token);
        String admin = extraction.getUserType();
        if (admin == null || !admin.equals("admin")) {
            throw new Exception("Administration page only");
        }
        adminService.deleteGame(gameId);
    }




}
