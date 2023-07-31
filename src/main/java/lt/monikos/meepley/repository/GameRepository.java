package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g " +
            "WHERE " +
            "(:category IS NULL OR g.category IN(:category)) " +
            "AND " +
            "(:complexity IS NULL OR g.complexity IN(:complexity)) " +
            "AND " +
            "(:title IS NULL OR g.title LIKE LOWER(CONCAT('%', :title,'%')))")
    List<Game> findGames(@RequestParam("category") String category, @RequestParam("complexity") String complexity, @RequestParam("title") String title);


}
