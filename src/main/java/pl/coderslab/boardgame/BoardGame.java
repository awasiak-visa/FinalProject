package pl.coderslab.boardgame;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Difficulty;

import java.util.Set;

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
    private Integer playerCount;
    private Integer time;
    private Difficulty difficulty;
    @ManyToMany
    private Set<Category> categories;
    private Double rating;
}
