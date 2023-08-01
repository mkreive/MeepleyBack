package lt.monikos.meepley.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "reviews")
@Data
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "date")
    @CreationTimestamp
    private Date date;

    @Column(name = "game_id")
    private Long gameId;

    @Column(name = "game_review")
    private String gameReview;

}
