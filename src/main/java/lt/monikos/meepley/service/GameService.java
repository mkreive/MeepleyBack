package lt.monikos.meepley.service;

import lt.monikos.meepley.entity.Game;
import lt.monikos.meepley.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public List<Game> getByTitle(String gameTitle) {
        return gameRepository.findAll()
                .stream()
                .filter(g -> g.getTitle().equalsIgnoreCase(gameTitle))
                .toList();
    }

    public List<Game> getByCategory(String category) {
        return gameRepository.findAll()
                .stream()
                .filter(g -> g.getCategory().equalsIgnoreCase(category))
                .toList();
    }


}