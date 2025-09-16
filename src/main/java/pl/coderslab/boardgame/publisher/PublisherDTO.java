package pl.coderslab.boardgame.publisher;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PublisherDTO {
    private Long id;
    private String name;
}
