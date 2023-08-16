package lt.monikos.meepley;

import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.repository.CheckoutRepository;
import lt.monikos.meepley.repository.GameRepository;
import lt.monikos.meepley.repository.ReviewRepository;
import lt.monikos.meepley.requestModels.AddGameRequest;
import lt.monikos.meepley.service.AdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AdminServiceTests {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private CheckoutRepository checkoutRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostGame() {
        AddGameRequest addGameRequest = new AddGameRequest();
        addGameRequest.setTitle("Title");
        addGameRequest.setDesigner("Designer");
        addGameRequest.setPublisher("Publisher");
        addGameRequest.setIntro("Intro");
        addGameRequest.setDescription("Description");
        addGameRequest.setCategory("CATEGORY");
        addGameRequest.setComplexity("easy");
        addGameRequest.setCopies(20);
        addGameRequest.setPlayers("1-2");
        addGameRequest.setPlayingTime("20-40");
        addGameRequest.setImg("image".getBytes());

        adminService.postGame(addGameRequest);
        verify(gameRepository).save(any(Game.class));
    }

    @Test
    void testEditGame() throws Exception {
        Long gameId = 1L;
        AddGameRequest addGameRequest = new AddGameRequest();
        addGameRequest.setTitle("Title");
        addGameRequest.setDesigner("Designer");
        addGameRequest.setPublisher("Publisher");
        addGameRequest.setIntro("Intro");
        addGameRequest.setDescription("Description");
        addGameRequest.setCategory("CATEGORY");
        addGameRequest.setComplexity("easy");
        addGameRequest.setCopies(20);
        addGameRequest.setPlayers("1-2");
        addGameRequest.setPlayingTime("20-40");
        addGameRequest.setImg("image".getBytes());

        Game existingGame = new Game();
        existingGame.setId(gameId);
        existingGame.setTitle("Other Title");
        existingGame.setDesigner("Other Designer");
        existingGame.setPublisher("Other Publisher");
        existingGame.setIntro("Other Intro");
        existingGame.setDescription("Other Description");
        existingGame.setCategory("CATEGORY");
        existingGame.setComplexity("medium");
        existingGame.setCopies(10);
        existingGame.setPlayers("2-4");
        existingGame.setPlayingTime("20-40");
        existingGame.setImg("image".getBytes());

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));
        adminService.editGame(gameId, addGameRequest);

        verify(gameRepository).save(existingGame);
    }



    @Test
    void testEditGame_GameNotFound() {
        Long gameId = 1L;
        AddGameRequest addGameRequest = new AddGameRequest();
        addGameRequest.setTitle("Title");
        addGameRequest.setDesigner("Designer");
        addGameRequest.setPublisher("Publisher");
        addGameRequest.setIntro("Intro");
        addGameRequest.setDescription("Description");
        addGameRequest.setCategory("CATEGORY");
        addGameRequest.setComplexity("easy");
        addGameRequest.setCopies(20);
        addGameRequest.setPlayers("1-2");
        addGameRequest.setPlayingTime("20-40");
        addGameRequest.setImg("image".getBytes());

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> adminService.editGame(gameId, addGameRequest));
    }

    @Test
    void testIncreaseGameQuantity() throws Exception {
        Long gameId = 1L;
        int initialCopiesAvailable = 5;

        Game existingGame = new Game();
        existingGame.setId(gameId);
        existingGame.setTitle("Title");
        existingGame.setDesigner("Designer");
        existingGame.setPublisher("Publisher");
        existingGame.setIntro("Intro");
        existingGame.setDescription("Description");
        existingGame.setCategory("CATEGORY");
        existingGame.setComplexity("medium");
        existingGame.setCopies(5);
        existingGame.setPlayers("2-4");
        existingGame.setPlayingTime("20-40");
        existingGame.setImg("image".getBytes());
        existingGame.setCopiesAvailable(initialCopiesAvailable);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));

        adminService.increaseGameQuantity(gameId);

        assertEquals(initialCopiesAvailable + 1, existingGame.getCopiesAvailable());
        verify(gameRepository).save(existingGame);
    }

    @Test
    void testDecreaseGameQuantity() throws Exception {
        Long gameId = 1L;
        int initialCopiesAvailable = 5;

        Game existingGame = new Game();
        existingGame.setId(gameId);
        existingGame.setTitle("Title");
        existingGame.setDesigner("Designer");
        existingGame.setPublisher("Publisher");
        existingGame.setIntro("Intro");
        existingGame.setDescription("Description");
        existingGame.setCategory("CATEGORY");
        existingGame.setComplexity("medium");
        existingGame.setCopies(5);
        existingGame.setPlayers("2-4");
        existingGame.setPlayingTime("20-40");
        existingGame.setImg("image".getBytes());
        existingGame.setCopiesAvailable(initialCopiesAvailable);

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));

        adminService.decreaseGameQuantity(gameId);

        assertEquals(initialCopiesAvailable - 1, existingGame.getCopiesAvailable());
        verify(gameRepository).save(existingGame);
    }

    @Test
    void testDecreaseGameQuantity_GameNotFound() {
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> adminService.decreaseGameQuantity(gameId));
    }

    @Test
    void testDeleteGame() throws Exception {
        Long gameId = 1L;

        Game existingGame = new Game();
        existingGame.setId(gameId);
        existingGame.setTitle("Title");
        existingGame.setDesigner("Designer");
        existingGame.setPublisher("Publisher");
        existingGame.setIntro("Intro");
        existingGame.setDescription("Description");
        existingGame.setCategory("CATEGORY");
        existingGame.setComplexity("medium");
        existingGame.setCopies(5);
        existingGame.setPlayers("2-4");
        existingGame.setPlayingTime("20-40");
        existingGame.setImg("image".getBytes());

        when(gameRepository.findById(gameId)).thenReturn(Optional.of(existingGame));

        adminService.deleteGame(gameId);

        verify(gameRepository).delete(existingGame);
        verify(checkoutRepository).deleteAllByGameId(gameId);
        verify(reviewRepository).deleteAllByGameId(gameId);
    }

    @Test
    void testDeleteGame_GameNotFound() {
        Long gameId = 1L;

        when(gameRepository.findById(gameId)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> adminService.deleteGame(gameId));
    }


}
