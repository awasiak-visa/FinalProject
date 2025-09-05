package pl.coderslab.play;

import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Status;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.cafe.Cafe;


import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PlayDTO {
    private Long id;
    private BoardGame boardGame ;
    private LocalDateTime dateTime;
    private List<String> cafeInfo;
    private List<String> usersNames;
    private Status status;
}
