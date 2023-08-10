package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByGameId(@RequestParam("game_id") Long gameId);

    Review findByUserEmailAndGameId(String userEmail, Long bookId);

    @Modifying
    @Query("DELETE FROM Review WHERE gameId in :game_id")
    void deleteAllByGameId(@Param("game_id") Long gameId);


}
