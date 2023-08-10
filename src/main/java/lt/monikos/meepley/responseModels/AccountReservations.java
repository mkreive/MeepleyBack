package lt.monikos.meepley.responseModels;

import lombok.Data;
import lt.monikos.meepley.entity.Game;

@Data
public class AccountReservations {

    private Game game;

    private int daysLeft;

    public AccountReservations(Game game, int daysLeft) {
        this.game = game;
        this.daysLeft = daysLeft;
    }
}
