package pl.coderslab.boardgame;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Difficulty;
import pl.coderslab.boardgame.category.Category;
import pl.coderslab.boardgame.publisher.Publisher;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "boardgames", uniqueConstraints = @UniqueConstraint(columnNames = {"title", "publisher_id"}))
public class BoardGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    private Publisher publisher;
    private String description;
    private Integer minPlayerCount;
    private Integer maxPlayerCount;
    private Integer minTime;
    private Integer maxTime;
    private Difficulty difficulty;
    @ManyToMany
    private List<Category> categories;
    private Double rating;
}
