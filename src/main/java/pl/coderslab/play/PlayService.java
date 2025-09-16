package pl.coderslab.play;

import org.springframework.stereotype.Service;
import pl.coderslab.Status;
import pl.coderslab.user.User;
import java.util.List;
import java.util.Optional;

@Service
public class PlayService {

    private final PlayRepository playRepository;

    public PlayService(PlayRepository playRepository) {
        this.playRepository = playRepository;
    }

    public void createPlay(Play play) {
        playRepository.save(play);
    }

    public Optional<Play> readPlayById(Long id) {
        return playRepository.findById(id);
    }

    public void updatePlay(Play play) {
        playRepository.update(play.getBoardGame(), play.getDateTime(), play.getCafe(), play.getUsers(),
                play.getStatus(), play.getId());
    }

    public void deletePlayById(Long id) {
        playRepository.deleteById(id);
    }

    public List<Play> readAllPlays() {
        return playRepository.findAll();
    }

    void updatePlayStatus(Status status, Long id) {
        playRepository.updateStatus(status, id);
    }

    void updatePlayUsersAdd(User user, Long id) {
        List<User> users = playRepository.findById(id).get().getUsers();
        users.add(user);
        playRepository.updateUsers(users, id);
    }
    void updatePlayUsersRemove(User user, Long id) {
        List<User> users = playRepository.findById(id).get().getUsers();
        users.remove(user);
        playRepository.updateUsers(users, id);
    }
    Optional<List<Play>> findPlaysByUserId(Long id) {
        return playRepository.findByUserId(id);
    }

    Optional<List<Play>> findOpenPlaysByCafeId(Long id, Status OPEN) {
        return playRepository.findOpenByCafeId(id, OPEN);
    }

    Optional<List<Play>> findOpenPlaysByBoardGameId(Long id, Status OPEN) {
        return playRepository.findOpenByBoardGameId(id, OPEN);
    }
    Optional<List<Play>> findOpenPlaysByBoardGameNameAndPublisherName(String boardGameTitle, String publisherName,
                                                                      Status OPEN) {
        return playRepository.findOpenByBoardGameTitleAndPublisherName(boardGameTitle, publisherName, OPEN);
    }

}
