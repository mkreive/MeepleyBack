package lt.monikos.meepley;

import lt.monikos.meepley.entity.Checkout;
import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.entity.History;
import lt.monikos.meepley.repository.CheckoutRepository;
import lt.monikos.meepley.repository.GameRepository;
import lt.monikos.meepley.repository.HistoryRepository;
import lt.monikos.meepley.responseModels.AccountReservations;
import lt.monikos.meepley.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GameServiceTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private CheckoutRepository checkoutRepository;

    @Mock
    private HistoryRepository historyRepository;

    @InjectMocks
    private GameService gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCheckoutGame() throws Exception {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        Game game = new Game();
        game.setId(gameId);
        game.setTitle("Title");
        game.setDesigner("Designer");
        game.setPublisher("Publisher");
        game.setIntro("Intro");
        game.setDescription("Description");
        game.setCategory("CATEGORY");
        game.setComplexity("easy");
        game.setCopies(20);
        game.setCopiesAvailable(20);
        game.setPlayers("1-2");
        game.setPlayingTime("20-40");
        game.setImg("image".getBytes());

        Checkout validateCheckout = null;

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(checkoutRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(validateCheckout);

        Game checkedOutGame = gameService.checkoutGame(userEmail, gameId);

        assertEquals(game.getCopiesAvailable() - 1, checkedOutGame.getCopiesAvailable() - 1);
        verify(gameRepository).save(game);
        verify(checkoutRepository).save(any(Checkout.class));
    }

    @Test
    void testCheckoutGame_GameNotFound() {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> gameService.checkoutGame(userEmail, gameId));
    }

    @Test
    void testCheckoutGame_GameAlreadyReserved() {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        Game game = new Game();
        game.setId(gameId);
        game.setTitle("Title");
        game.setDesigner("Designer");
        game.setPublisher("Publisher");
        game.setIntro("Intro");
        game.setDescription("Description");
        game.setCategory("CATEGORY");
        game.setComplexity("easy");
        game.setCopies(20);
        game.setCopiesAvailable(20);
        game.setPlayers("1-2");
        game.setPlayingTime("20-40");
        game.setImg("image".getBytes());

        Checkout validateCheckout = new Checkout();
        validateCheckout.setId(1L);
        validateCheckout.setUserEmail(userEmail);
        validateCheckout.setCheckoutDate("2011.01.01");
        validateCheckout.setReturnDate("2011.01.08");
        validateCheckout.setGameId(gameId);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(checkoutRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(validateCheckout);

        assertThrows(Exception.class, () -> gameService.checkoutGame(userEmail, gameId));
    }

    @Test
    void testCurrentReservations() throws Exception {
        String userEmail = "user@example.com";
        Long gameId1 = 1L;
        Long gameId2 = 2L;

        List<Checkout> checkoutList = new ArrayList<>();

        Checkout checkoutByUser1 = new Checkout();
        checkoutByUser1.setId(1L);
        checkoutByUser1.setUserEmail(userEmail);
        checkoutByUser1.setCheckoutDate("2011-01-01");
        checkoutByUser1.setReturnDate("2011-01-08");
        checkoutByUser1.setGameId(gameId1);
        checkoutList.add(checkoutByUser1);

        Checkout checkoutByUser2 = new Checkout();
        checkoutByUser2.setId(2L);
        checkoutByUser2.setUserEmail(userEmail);
        checkoutByUser2.setCheckoutDate("2011-01-01");
        checkoutByUser2.setReturnDate("2011-01-08");
        checkoutByUser2.setGameId(gameId2);
        checkoutList.add(checkoutByUser2);

        List<Long> gameIdList = new ArrayList<>();
        gameIdList.addAll(Arrays.asList(gameId1, gameId2));

        List<Game> games = new ArrayList<>();
        Game checkedGame = new Game();
        checkedGame.setId(gameId1);
        checkedGame.setTitle("Title");
        checkedGame.setDesigner("Designer");
        checkedGame.setPublisher("Publisher");
        checkedGame.setIntro("Intro");
        checkedGame.setDescription("Description");
        checkedGame.setCategory("CATEGORY");
        checkedGame.setComplexity("easy");
        checkedGame.setCopies(20);
        checkedGame.setCopiesAvailable(20);
        checkedGame.setPlayers("1-2");
        checkedGame.setPlayingTime("20-40");
        checkedGame.setImg("image".getBytes());
        games.add(checkedGame);

        Game checkedGame2 = new Game();
        checkedGame2.setId(gameId2);
        checkedGame2.setTitle("Other title");
        checkedGame2.setDesigner("Other designer");
        checkedGame2.setPublisher("Other publisher");
        checkedGame2.setIntro("Other intro");
        checkedGame2.setDescription("Other description");
        checkedGame2.setCategory("CATEGORY");
        checkedGame2.setComplexity("medium");
        checkedGame2.setCopies(12);
        checkedGame2.setCopiesAvailable(12);
        checkedGame2.setPlayers("1-2");
        checkedGame2.setPlayingTime("20-40");
        checkedGame2.setImg("image".getBytes());
        games.add(checkedGame2);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Game game : games) {
            Date returnDate = sdf.parse(LocalDate.now().plusDays(3).toString());
            Checkout checkout = new Checkout(userEmail, LocalDate.now().toString(), sdf.format(returnDate), game.getId());
            checkoutList.add(checkout);
            gameIdList.add(game.getId());
        }

        when(checkoutRepository.findGamesByUserEmail(userEmail)).thenReturn(checkoutList);
        when(gameRepository.findGamesByGameIds(gameIdList)).thenReturn(games);

        List<AccountReservations> reservations = gameService.currentReservations(userEmail);

        assertEquals(games.size(), reservations.size());
    }

    @Test
    void testReturnGame() throws Exception {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        Game game = new Game();
        game.setId(gameId);
        game.setTitle("Title");
        game.setDesigner("Designer");
        game.setPublisher("Publisher");
        game.setIntro("Intro");
        game.setDescription("Description");
        game.setCategory("CATEGORY");
        game.setComplexity("easy");
        game.setCopies(20);
        game.setCopiesAvailable(20);
        game.setPlayers("1-2");
        game.setPlayingTime("20-40");
        game.setImg("image".getBytes());

        Checkout validateCheckout = new Checkout(userEmail, LocalDate.now().toString(), LocalDate.now().plusDays(7).toString(), gameId);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(checkoutRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(validateCheckout);

        gameService.returnGame(userEmail, gameId);

        verify(gameRepository).save(game);
        verify(checkoutRepository).deleteById(validateCheckout.getId());
        verify(historyRepository).save(any(History.class));
    }

    @Test
    void testReturnGame_GameNotFound() {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> gameService.returnGame(userEmail, gameId));
    }

    @Test
    void testReturnGame_CheckoutNotFound() {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        Game game = new Game();
        game.setId(gameId);
        game.setTitle("Title");
        game.setDesigner("Designer");
        game.setPublisher("Publisher");
        game.setIntro("Intro");
        game.setDescription("Description");
        game.setCategory("CATEGORY");
        game.setComplexity("easy");
        game.setCopies(20);
        game.setCopiesAvailable(20);
        game.setPlayers("1-2");
        game.setPlayingTime("20-40");
        game.setImg("image".getBytes());

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(game));
        when(checkoutRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(null);

        assertThrows(Exception.class, () -> gameService.returnGame(userEmail, gameId));
    }

    @Test
    void testRenewLoan() throws Exception {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        Checkout validateCheckout = new Checkout(userEmail, LocalDate.now().toString(), LocalDate.now().plusDays(7).toString(), gameId);

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date initialReturnDate = sdFormat.parse(validateCheckout.getReturnDate());

        when(checkoutRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(validateCheckout);

        gameService.renewLoan(userEmail, gameId);

        Date newReturnDate = sdFormat.parse(validateCheckout.getReturnDate());

        assertTrue(newReturnDate.after(initialReturnDate) || newReturnDate.equals(initialReturnDate));
        verify(checkoutRepository).save(validateCheckout);
    }

    @Test
    void testRenewLoan_CheckoutNotFound() {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        when(checkoutRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(null);

        assertThrows(Exception.class, () -> gameService.renewLoan(userEmail, gameId));
    }

}



