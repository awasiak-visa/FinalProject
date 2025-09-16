package pl.coderslab.review;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.user.User;

@Entity
@Getter
@Setter
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private BoardGame boardGame;
    private Double score;
    private String title;
    private String description;
    @ManyToOne
    private User user;
}
