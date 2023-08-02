package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndGameId(String userEmail, Long gameId);

    List<Checkout> findGamesByUserEmail(String userEmail);
}
