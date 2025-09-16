package pl.coderslab.review;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDTO {
    private Long id;
    private String boardGameTitle;
    private Double score;
    private String title;
    private String description;
    private String username;
}
