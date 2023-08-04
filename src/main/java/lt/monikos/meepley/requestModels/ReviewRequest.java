package lt.monikos.meepley.requestModels;

import lombok.Data;
import java.util.Optional;

@Data
public class ReviewRequest {

    private double rating;

    private Long gameId;

    private Optional<String> gameReview;

}
