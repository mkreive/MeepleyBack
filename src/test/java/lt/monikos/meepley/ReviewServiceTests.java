package lt.monikos.meepley;

import lt.monikos.meepley.entity.Review;
import lt.monikos.meepley.repository.ReviewRepository;
import lt.monikos.meepley.requestModels.ReviewRequest;
import lt.monikos.meepley.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPostReview() throws Exception {
        String userEmail = "user@example.com";
        Long gameId = 1L;
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setGameId(gameId);
        reviewRequest.setRating(5.0);
        reviewRequest.setGameReview("Game review".describeConstable());

        Review existingReview = null;

        when(reviewRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(existingReview);

        reviewService.postReview(userEmail, reviewRequest);

        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void testPostReview_ReviewAlreadyExists() {
        String userEmail = "user@example.com";
        Long gameId = 1L;
        ReviewRequest reviewRequest = new ReviewRequest();
        reviewRequest.setGameId(gameId);
        reviewRequest.setRating(5.0);
        reviewRequest.setGameReview("Game review".describeConstable());

        Review existingReview = new Review();
        existingReview.setId(1L);
        existingReview.setGameId(gameId);
        existingReview.setRating(5.0);
        existingReview.setGameReview("Existing Game review");
        existingReview.setUserEmail(userEmail);
        existingReview.setDate(new Date(2011, 11, 11));

        when(reviewRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(existingReview);

        assertThrows(Exception.class, () -> reviewService.postReview(userEmail, reviewRequest));
    }

    @Test
    void testUserReviewListed_ReviewExists() {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        Review existingReview = new Review();
        existingReview.setId(1L);
        existingReview.setGameId(gameId);
        existingReview.setRating(4.0);
        existingReview.setGameReview("Existing Game review");
        existingReview.setUserEmail(userEmail);
        existingReview.setDate(new Date(2011, 11, 11));

        when(reviewRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(existingReview);

        assertTrue(reviewService.userReviewListed(userEmail, gameId));
    }

    @Test
    void testUserReviewListed_ReviewDoesNotExist() {
        String userEmail = "user@example.com";
        Long gameId = 1L;

        when(reviewRepository.findByUserEmailAndGameId(userEmail, gameId)).thenReturn(null);

        assertFalse(reviewService.userReviewListed(userEmail, gameId));
    }
}
