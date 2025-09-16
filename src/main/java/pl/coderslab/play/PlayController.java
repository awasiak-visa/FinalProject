package pl.coderslab.play;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.coderslab.user.User;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/plays")
public class PlayController {

    private final PlayService playService;

    public PlayController(PlayService playService) {
        this.playService = playService;
    }

    private PlayDTO convertPlayToDTO(Play play) {
        return PlayDTO.builder().id(play.getId())
                .boardGameInfo(List.of(play.getBoardGame().getTitle(), play.getBoardGame().getPublisher().getName()))
                .dateTime(play.getDateTime())
                .cafeInfo(List.of(play.getCafe().getName(), play.getCafe().getAddress()))
                .usernames(play.getUsers().stream().map(User::getUsername).collect(Collectors.toList()))
                .status(play.getStatus()).freePlaces(play.getFreePlaces())
                .build();
    }
}
