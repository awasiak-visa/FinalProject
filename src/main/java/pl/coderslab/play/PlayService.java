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
        play.setStatus(Status.OPEN);
        play.setFreePlaces(play.getBoardGame().getMaxPlayerCount() - 1);
        playRepository.save(play);
    }

    public Optional<Play> readPlayById(Long id) {
        return playRepository.findById(id);
    }

    public void updatePlay(Play play) {
        Status status = play.getStatus();
        if (play.getUsers().size() >= play.getBoardGame().getMaxPlayerCount()) {
            status = Status.CLOSED;
        }
        playRepository.update(play.getBoardGame(), play.getDateTime(), play.getCafe(), play.getUsers(),
                status, play.getBoardGame().getMaxPlayerCount() - play.getUsers().size(),
                play.getId());
        updatePlayStatusToPast();
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
    public void updatePlayStatusToPast() {
        playRepository.updateStatusToPast(LocalDateTime.now());
    }

    public void updatePlayUsersAdd(User user, Long id) {
        if (playRepository.findById(id).isPresent()) {
            List<User> users = playRepository.findById(id).get().getUsers();
            users.add(user);
            playRepository.updateUsers(users, id);
            Integer freePlaces = playRepository.findById(id).get().getFreePlaces();
            playRepository.updateFreePlaces(freePlaces - 1, id);
            if (playRepository.findById(id).get().getFreePlaces() == 0) {
                playRepository.updateStatus(Status.CLOSED, id);
            }
        }
    }

    public void updatePlayUsersRemove(User user, Long id) {
        if (playRepository.findById(id).isPresent()) {
            List<User> users = playRepository.findById(id).get().getUsers();
            users.remove(user);
            playRepository.updateUsers(users, id);
            if (playRepository.findById(id).get().getFreePlaces() == 0) {
                playRepository.updateStatus(Status.OPEN, id);
            }
            Integer freePlaces = playRepository.findById(id).get().getFreePlaces();
            playRepository.updateFreePlaces(freePlaces + 1, id);
        }
    }
}
