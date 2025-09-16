package pl.coderslab.boardgame;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Difficulty;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@Builder
public class BoardGameDTO {
    private Long id;
    private String title;
    private String publisherName;
    private String description;
    private Integer minPlayerCount;
    private Integer maxPlayerCount;
    private Integer minTime;
    private Integer maxTime;
    private Difficulty difficulty;
    private List<String> categoriesNames;
    private Double rating;
}
