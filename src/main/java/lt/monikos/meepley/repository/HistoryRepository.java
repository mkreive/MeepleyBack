package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

public interface HistoryRepository extends JpaRepository<History, Long> {
    History findGamesByUserEmail(@RequestParam("email") String userEmail);
}
