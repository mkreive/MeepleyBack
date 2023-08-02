package lt.monikos.meepley.entity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "checkouts")
public class Checkout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "checkout_date")
    private String checkoutDate;

    @Column(name = "return_date")
    private String returnDate;

    @Column(name = "game_id")
    private Long gameId;

    public Checkout() {
    }
    public Checkout(String userEmail, String checkoutDate, String returnDate, Long gameId) {
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.returnDate = returnDate;
        this.gameId = gameId;
    }
}
