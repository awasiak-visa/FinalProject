package pl.coderslab.play;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Status;
import pl.coderslab.boardgame.BoardGame;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PlayDTO {

    private Long id;
    private BoardGame boardGame;
    private LocalDateTime dateTime;
    private List<String> cafeInfo;
    private List<String> usersNames;
    private Status status;
}
