package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
