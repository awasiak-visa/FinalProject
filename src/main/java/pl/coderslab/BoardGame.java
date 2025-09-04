package pl.coderslab;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "boardgames")
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
