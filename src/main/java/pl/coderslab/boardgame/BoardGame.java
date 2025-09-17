package pl.coderslab.boardgame;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.UniqueElements;
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
    @Size(min = 1, max = 40)
    @NotNull
    private String title;
    @ManyToOne
    @NotNull
    private Publisher publisher;
    private String description;
    @Min(1)
    @NotNull
    private Integer minPlayerCount;
    @NotNull
    private Integer maxPlayerCount;
    @Min(5)
    @NotNull
    private Integer minTime;
    @NotNull
    private Integer maxTime;
    private Difficulty difficulty;
    @ManyToMany
    @UniqueElements
    private List<Category> categories;
    @Range(min = 1, max = 10)
    private Double rating;
}
