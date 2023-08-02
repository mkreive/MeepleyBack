package lt.monikos.meepley.service;

import lt.monikos.meepley.entity.Checkout;
import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.repository.CheckoutRepository;
import lt.monikos.meepley.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;

    private CheckoutRepository checkoutRepository;

    @Autowired
    public GameService(GameRepository gameRepository, CheckoutRepository checkoutRepository) {
        this.gameRepository = gameRepository;
        this.checkoutRepository = checkoutRepository;
    }

    public Game checkoutGame (String userEmail, Long gameId) throws Exception {

        Optional<Game> game = gameRepository.findById(gameId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndGameId(userEmail, gameId);

        if (!game.isPresent() || validateCheckout != null || game.get().getCopiesAvailable() <= 0) {
            throw new Exception("Game doesn't exist or already reserved by user");
        }

        game.get().setCopiesAvailable(game.get().getCopiesAvailable() - 1);
        gameRepository.save(game.get());

        Checkout checkout = new Checkout(
                userEmail,
                LocalDate.now().toString(),
                LocalDate.now().plusDays(7).toString(),
                game.get().getId()
        );

        checkoutRepository.save(checkout);

        return game.get();
    }

    public Boolean checkoutGameByUser(String userEmail, Long bookId) {
        Checkout validateCheckout = checkoutRepository.findByUserEmailAndGameId(userEmail, bookId);
        return validateCheckout != null;
    }

    public int currentLoansCount(String userEmail) {
        return checkoutRepository.findGamesByUserEmail(userEmail).size();
    }



}
