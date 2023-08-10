package lt.monikos.meepley.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "designer")
    private String designer;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "intro")
    private String intro;

    @Column(name = "description")
    private String description;

    @Column(name = "copies")
    private int copies;

    @Column(name = "copies_available")
    private int copiesAvailable;

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
}
