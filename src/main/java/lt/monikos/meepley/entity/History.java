package lt.monikos.meepley.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "History")
@Data
public class History {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name="user_email")
    private String userEmail;

    @Column(name="checkout_date")
    private String checkoutDate;

    @Column(name="returned_date")
    private String returnedDate;

    @Column(name="title")
    private String title;

    @Column(name = "designer")
    private String designer;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "intro")
    private String intro;

    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private String category;

    @Column(name = "complexity")
    private String complexity;

    @Column(name = "players")
    private String players;

    @Column(name = "playing_time")
    private String playingTime;

    @Column(name = "img")
    private byte[] img;

    public History(){}

    public History(String userEmail, String checkoutDate, String returnedDate, String title,
                   String designer, String publisher, String intro, String description,
                   String category, String complexity, String players, String playingTime,
                   byte[] img) {
        this.userEmail = userEmail;
        this.checkoutDate = checkoutDate;
        this.returnedDate = returnedDate;
        this.title = title;
        this.designer = designer;
        this.publisher = publisher;
        this.intro = intro;
        this.description = description;
        this.category=category;
        this.complexity = complexity;
        this.players = players;
        this.playingTime = playingTime;
        this.img = img;
    }
}