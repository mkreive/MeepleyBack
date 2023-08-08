package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findGamesByUserEmail(@RequestParam("email") String userEmail);
}
