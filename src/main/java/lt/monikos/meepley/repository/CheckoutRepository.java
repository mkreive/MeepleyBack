package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndGameId(String userEmail, Long gameId);

    List<Checkout> findGamesByUserEmail(String userEmail);

    @Modifying
    @Query("DELETE FROM Checkout WHERE gameId in :game_id")
    void deleteAllByGameId(@Param("game_id") Long gameId);
}
