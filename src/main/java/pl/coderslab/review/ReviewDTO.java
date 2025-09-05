package pl.coderslab.review;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ReviewDTO {
    private Long id;
    private String title;
    private String description;
    private String userName;
}
