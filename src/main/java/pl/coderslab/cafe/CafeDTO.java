package pl.coderslab.cafe;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.boardgame.BoardGame;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
public class CafeDTO {
    private Long id;
    private String name;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String address;
    private List<BoardGame> boardGames;
}
