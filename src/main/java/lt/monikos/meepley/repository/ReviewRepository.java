package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByGameId(@RequestParam("game_id") Long gameId);

    Review findByUserEmailAndGameId(String userEmail, Long bookId);


}
