package lt.monikos.meepley.service;

import lt.monikos.meepley.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class GameService {

    private GameRepository gameRepository;

    @Autowired
    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

//    public List<Game> getByTitle(String gameTitle) {
//        return gameRepository.findAll()
//                .stream()
//                .filter(g -> g.getTitle().matches("(?i)^" + Pattern.quote(gameTitle) + ".*"))
//                .collect(Collectors.toList());
//    }
//
//    public List<Game> getByCategory(String categories) {
//        String[] categoriesList = categories.split("\\s*,\\s*");
//        List<Game> foundGames = new ArrayList<>();
//
//        for (String category: categoriesList) {
//            List<Game> found = gameRepository.findAll()
//                    .stream()
//                    .filter(g -> g.getCategory().equalsIgnoreCase(category))
//                    .toList();
//            foundGames.addAll(found);
//        }
//        return foundGames;
//    }
//
//    public List<Game> getByComplexity(String complexity) {
//        String[] categoriesList = complexity.split("\\s*,\\s*");
//        List<Game> foundGames = new ArrayList<>();
//
//        for (String category: categoriesList) {
//            List<Game> found = gameRepository.findAll()
//                    .stream()
//                    .filter(g -> g.getComplexity().equalsIgnoreCase(category))
//                    .toList();
//            foundGames.addAll(found);
//        }
//        return foundGames;
//    }



}
