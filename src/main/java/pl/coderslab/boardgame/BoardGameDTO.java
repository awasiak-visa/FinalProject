package pl.coderslab.boardgame;

import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Difficulty;

import java.util.Set;

@Getter
@Setter
public class BoardGameDTO {
    private Long id;
    private String title;
    private String publisherName;
    private String description;
    private Integer playerCount;
    private Integer time;
    private Difficulty difficulty;
    private Set<String> categoriesNames;
    private Double rating;
}
