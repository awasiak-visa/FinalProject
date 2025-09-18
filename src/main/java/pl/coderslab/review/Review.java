package pl.coderslab.review;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
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
    @NotNull
    private BoardGame boardGame;
    @Range(min = 1, max = 10)
    @NotNull
    private Integer rating;
    private String title;
    private String description;
    @ManyToOne
    @NotNull
    private User user;
}
