package lt.monikos.meepley.service;

import lt.monikos.meepley.entity.Review;
import lt.monikos.meepley.repository.ReviewRepository;
import lt.monikos.meepley.requestModels.ReviewRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
@Transactional
public class ReviewService {

    private ReviewRepository reviewRepository;

    @Autowired
    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void postReview(String userEmail, ReviewRequest reviewRequest) throws Exception {
        Review validateReview = reviewRepository.findByUserEmailAndGameId(userEmail, reviewRequest.getGameId());
        if (validateReview != null) {
            throw new Exception("Review already created!");
        }

        Review review = new Review();
        review.setGameId(reviewRequest.getGameId());
        review.setRating(reviewRequest.getRating());
        review.setUserEmail(userEmail);
        if(reviewRequest.getGameReview().isPresent()) {
            review.setGameReview(reviewRequest.getGameReview()
                    .map(Object::toString)
                    .orElse(null));
        }

        review.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        reviewRepository.save(review);
    }

    public Boolean userReviewListed(String userEmail, Long gameId) {
        Review validateReview = reviewRepository.findByUserEmailAndGameId(userEmail, gameId);
        return validateReview != null;
    }

}
