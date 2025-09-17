package pl.coderslab.play;

import org.springframework.stereotype.Service;
import pl.coderslab.Status;
import pl.coderslab.user.User;
import java.time.LocalDateTime;
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

    // finding methods
    public Optional<List<Play>> findPlaysByUserId(Long userId) {
        return playRepository.findByUserId(userId);
    }

    public Optional<List<Play>> findOpenPlaysByCafeId(Long cafeId) {
        return playRepository.findOpenByCafeId(cafeId);
    }

    public Optional<List<Play>> findOpenPlaysByBoardGameId(Long boardGameId) {
        return playRepository.findOpenByBoardGameId(boardGameId);
    }

    public Optional<List<Play>> findOpenPlaysByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName) {
        return playRepository.findOpenByBoardGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    public Optional<List<Play>> findOpenPlaysByFreePlacesGreaterThanEqual(Integer peopleCount) {
        return playRepository.findOpenByFreePlacesGreaterThanEqual(peopleCount);
    }

    public Optional<List<Play>> findOpenPlays() {
        return playRepository.findOpen();
    }

    // updating methods
    public void updatePlayStatus(Status status, Long id) {
        playRepository.updateStatus(status, id);
    }

    public void updatePlayUsersAdd(User user, Long id) {
        if (playRepository.findById(id).isPresent()) {
            List<User> users = playRepository.findById(id).get().getUsers();
            users.add(user);
            playRepository.updateUsers(users, id);
        }
    }

    public void updatePlayUsersRemove(User user, Long id) {
        if (playRepository.findById(id).isPresent()) {
            List<User> users = playRepository.findById(id).get().getUsers();
            users.remove(user);
            playRepository.updateUsers(users, id);
        }
    }

    public void updatePlayFreePlaces(Integer freePlaces, Long id) {
        playRepository.updateFreePlaces(freePlaces, id);
    }
}
