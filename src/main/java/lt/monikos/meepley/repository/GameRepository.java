package lt.monikos.meepley.repository;

import lt.monikos.meepley.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    @Query("SELECT g FROM Game g " +
            "WHERE " +
            "(:#{null eq #category} = true  OR g.category IN :category) " +
            "AND " +
            "(:#{null eq #complexity} = true OR g.complexity IN :complexity) " +
            "AND " +
            "(:title IS NULL OR g.title LIKE LOWER(CONCAT('%', :title,'%')))")
    List<Game> findGames(@RequestParam("category") List<String> category, @RequestParam("complexity") List<String> complexity, @RequestParam("title") String title);

    @Query("SELECT g FROM Game g WHERE id in :game_ids")
    List<Game> findGamesByGameIds (@Param("game_ids") List<Long> gameId);
}
