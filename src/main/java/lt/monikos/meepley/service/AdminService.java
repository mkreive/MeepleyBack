package lt.monikos.meepley.service;

import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.repository.CheckoutRepository;
import lt.monikos.meepley.repository.GameRepository;
import lt.monikos.meepley.repository.ReviewRepository;
import lt.monikos.meepley.requestModels.AddGameRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class AdminService {

    private GameRepository gameRepository;

    private CheckoutRepository checkoutRepository;

    private ReviewRepository reviewRepository;

    @Autowired
    public AdminService(GameRepository gameRepository, CheckoutRepository checkoutRepository, ReviewRepository reviewRepository) {
        this.gameRepository = gameRepository;
        this.checkoutRepository = checkoutRepository;
        this.reviewRepository = reviewRepository;
    }

    public void postGame(AddGameRequest addGameRequest) {
        Game game = new Game();
        game.setTitle(addGameRequest.getTitle());
        game.setDesigner(addGameRequest.getDesigner());
        game.setPublisher(addGameRequest.getPublisher());
        game.setIntro(addGameRequest.getIntro());
        game.setDescription(addGameRequest.getDescription());
        game.setCopies(addGameRequest.getCopies());
        game.setCopiesAvailable(addGameRequest.getCopies());
        game.setCategory(addGameRequest.getCategory());
        game.setComplexity(addGameRequest.getComplexity());
        game.setPlayers(addGameRequest.getPlayers());
        game.setPlayingTime(addGameRequest.getPlayingTime());
        game.setImg(addGameRequest.getImg());

        gameRepository.save(game);
    }

    public void editGame(Long gameId, AddGameRequest addGameRequest) throws Exception {
        Optional<Game> game = gameRepository.findById(gameId);
        if (!game.isPresent()) {
            throw new Exception("Game not found");
        }
        game.get().setId(game.get().getId());
        game.get().setTitle(addGameRequest.getTitle());
        game.get().setDesigner(addGameRequest.getDesigner());
        game.get().setPublisher(addGameRequest.getPublisher());
        game.get().setIntro(addGameRequest.getIntro());
        game.get().setDescription(addGameRequest.getDescription());
        game.get().setCopies(addGameRequest.getCopies());
        game.get().setCopiesAvailable(addGameRequest.getCopies());
        game.get().setCategory(addGameRequest.getCategory());
        game.get().setComplexity(addGameRequest.getComplexity());
        game.get().setPlayers(addGameRequest.getPlayers());
        game.get().setPlayingTime(addGameRequest.getPlayingTime());
        game.get().setImg(game.get().getImg());
        gameRepository.save(game.get());

    }

    public void increaseGameQuantity(Long gameId) throws Exception {

        Optional<Game> game = gameRepository.findById(gameId);

        if (!game.isPresent()) {
            throw new Exception("Game not found");
        }

        game.get().setCopiesAvailable(game.get().getCopiesAvailable() + 1);
        game.get().setCopies(game.get().getCopies() + 1);

        gameRepository.save(game.get());
    }

    public void decreaseGameQuantity(Long gameId) throws Exception {

        Optional<Game> game = gameRepository.findById(gameId);

        if (!game.isPresent() || game.get().getCopiesAvailable() <= 0 || game.get().getCopies() <= 0) {
            throw new Exception("Game not found or quantity locked");
        }

        game.get().setCopiesAvailable(game.get().getCopiesAvailable() - 1);
        game.get().setCopies(game.get().getCopies() - 1);

        gameRepository.save(game.get());
    }

    public void deleteGame(Long gameId) throws Exception {

        Optional<Game> game = gameRepository.findById(gameId);

        if (!game.isPresent()) {
            throw new Exception("Game not found");
        }

        gameRepository.delete(game.get());
        checkoutRepository.deleteAllByGameId(gameId);
        reviewRepository.deleteAllByGameId(gameId);
    }


}
