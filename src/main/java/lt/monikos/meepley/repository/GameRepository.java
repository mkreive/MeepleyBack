package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByTitle(@RequestParam("title") String title);
    List<Game> findByCategory(@RequestParam("category") String category);
    List<Game> findByComplexity(@RequestParam("complexity") String complexity);

}
