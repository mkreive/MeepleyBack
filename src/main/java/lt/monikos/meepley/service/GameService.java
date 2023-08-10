package lt.monikos.meepley.service;

import lt.monikos.meepley.entity.Checkout;
import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.entity.History;
import lt.monikos.meepley.repository.CheckoutRepository;
import lt.monikos.meepley.repository.GameRepository;
import lt.monikos.meepley.repository.HistoryRepository;
import lt.monikos.meepley.responseModels.AccountReservations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;

    private CheckoutRepository checkoutRepository;

    private HistoryRepository historyRepository;

    @Autowired
    public GameService(GameRepository gameRepository, CheckoutRepository checkoutRepository, HistoryRepository historyRepository) {
        this.gameRepository = gameRepository;
        this.checkoutRepository = checkoutRepository;
        this.historyRepository = historyRepository;
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

    public List<AccountReservations> currentReservations(String userEmail) throws Exception {

        List<AccountReservations> accountReservationsResponse = new ArrayList<>();

        List<Checkout> checkoutList = checkoutRepository.findGamesByUserEmail(userEmail);
        List<Long> gameIdList = new ArrayList<>();

        for (Checkout i: checkoutList) {
            gameIdList.add(i.getGameId());
        }

        List<Game> games = gameRepository.findGamesByGameIds(gameIdList);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Game game : games) {
            Optional<Checkout> checkout = checkoutList.stream()
                    .filter(x -> x.getGameId() == game.getId()).findFirst();

            if (checkout.isPresent()) {

                Date d1 = sdf.parse(checkout.get().getReturnDate());
                Date d2 = sdf.parse(LocalDate.now().toString());

                TimeUnit time = TimeUnit.DAYS;

                long difference_In_Time = time.convert(d1.getTime() - d2.getTime(),
                        TimeUnit.MILLISECONDS);

                accountReservationsResponse.add(new AccountReservations(game, (int) difference_In_Time));
            }
        }
        return accountReservationsResponse;
    }

    public void returnGame (String userEmail, Long gameId) throws Exception {

        Optional<Game> game = gameRepository.findById(gameId);

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndGameId(userEmail, gameId);

        if (!game.isPresent() || validateCheckout == null) {
            throw new Exception("Game does not exist or not reserved by user");
        }

        game.get().setCopiesAvailable(game.get().getCopiesAvailable() + 1);

        gameRepository.save(game.get());
        checkoutRepository.deleteById(validateCheckout.getId());

        History history = new History(
                userEmail,
                validateCheckout.getCheckoutDate(),
                LocalDate.now().toString(),
                game.get().getTitle(),
                game.get().getDesigner(),
                game.get().getPublisher(),
                game.get().getIntro(),
                game.get().getCategory(),
                game.get().getComplexity(),
                game.get().getPlayers(),
                game.get().getPlayingTime(),
                game.get().getImg()

        );
        historyRepository.save(history);
    }

    public void renewLoan(String userEmail, Long gameId) throws Exception {

        Checkout validateCheckout = checkoutRepository.findByUserEmailAndGameId(userEmail, gameId);

        if (validateCheckout == null) {
            throw new Exception("Game does not exist or not reserved by user");
        }

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date d1 = sdFormat.parse(validateCheckout.getReturnDate());
        Date d2 = sdFormat.parse(LocalDate.now().toString());

        if (d1.compareTo(d2) > 0 || d1.compareTo(d2) == 0) {
            validateCheckout.setReturnDate(LocalDate.now().plusDays(7).toString());
            checkoutRepository.save(validateCheckout);
        }
    }





}
