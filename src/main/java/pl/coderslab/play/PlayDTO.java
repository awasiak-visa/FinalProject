package pl.coderslab.play;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.coderslab.Status;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class PlayDTO {
    private Long id;
    private List<String> boardGameInfo;
    private LocalDateTime dateTime;
    private List<String> cafeInfo;
    private List<String> usernames;
    private Status status;
    private Integer freePlaces;
}
