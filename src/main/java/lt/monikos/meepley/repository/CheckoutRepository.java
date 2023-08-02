package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndGameId(String userEmail, Long gameId);
}
