package pl.coderslab.play;

import org.springframework.data.jpa.repository.Query;
import pl.coderslab.Status;
import pl.coderslab.user.User;

import java.util.List;
import java.util.Optional;

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

    void updatePlayStatus(Long id, Status status) {
        playRepository.updateStatus(id, status);
    }

    void updatePlayUsersAddUserById(Long id, Long userId) {
        playRepository.updateUsersAddUserById(id, userId);
    }
    void updatePlayUsersRemoveUserById(Long id, Long userId) {
        playRepository.updateUsersRemoveUserById(id, userId);
    }

    Optional<List<User>> findPlaysByUserId(Long id) {
        return playRepository.findByUserId(id);
    }

    Optional<List<User>> findOpenPlaysByCafeId(Long id, Status OPEN) {
        return playRepository.findOpenByCafeId(id, OPEN);
    }

    Optional<List<User>> findOpenPlaysByBoardGameId(Long id, Status OPEN) {
        return playRepository.findOpenByBoardGameId(id, OPEN);
    }
    Optional<List<User>> findOpenPlaysByBoardGameNameAndPublisherName(String boardGameTitle, String publisherName,
                                                                      Status OPEN) {
        return playRepository.findOpenByBoardGameTitleAndPublisherName(boardGameTitle, publisherName, OPEN);
    }

}
