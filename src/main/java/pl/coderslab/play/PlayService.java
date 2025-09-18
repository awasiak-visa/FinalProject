package pl.coderslab.play;

import org.springframework.stereotype.Service;
import pl.coderslab.Status;
import pl.coderslab.user.User;

import java.time.LocalDateTime;
import java.util.List;

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

    public Play readPlayById(Long id) {
        updatePlayStatusToPast();
        return playRepository.findById(id).orElseThrow(() -> new RuntimeException("Play not found."));
    }

    public void updatePlay(Play play) {
        Play originalPlay = playRepository.findById(play.getId())
                .orElseThrow(() -> new RuntimeException("Play not found."));
        Status status = play.getStatus();
        if (play.getUsers().size() > play.getBoardGame().getMaxPlayerCount()) {
            throw new RuntimeException("Max player count exceeded.");
        } else if (play.getUsers().size() == play.getBoardGame().getMaxPlayerCount()) {
            status = Status.CLOSED;
        }
        originalPlay.setBoardGame(play.getBoardGame());
        originalPlay.setDateTime(play.getDateTime());
        originalPlay.setCafe(play.getCafe());
        originalPlay.setUsers(play.getUsers());
        originalPlay.setStatus(status);
        originalPlay.setFreePlaces(play.getBoardGame().getMaxPlayerCount() - play.getUsers().size());
        playRepository.save(originalPlay);
        if (originalPlay.getUsers().isEmpty()) {
            playRepository.deleteById(originalPlay.getId());
        }
    }

    public void deletePlayById(Long id) {
        if (playRepository.findById(id).isEmpty()) {
            throw new RuntimeException("Play not found.");
        }
        playRepository.deleteById(id);
    }

    public List<Play> readAllPlays() {
        updatePlayStatusToPast();
        if (playRepository.findAll().isEmpty()) {
            throw new RuntimeException("Plays not found.");
        }
        return playRepository.findAll();
    }

    // finding methods
    public List<Play> findPlaysByUserId(Long userId) {
        updatePlayStatusToPast();
        if (playRepository.findByUserId(userId).isEmpty()) {
            throw new RuntimeException("Plays not found.");
        }
        return playRepository.findByUserId(userId);
    }

    public List<Play> findOpenPlaysByCafeId(Long cafeId) {
        updatePlayStatusToPast();
        if (playRepository.findOpenByCafeId(cafeId).isEmpty()) {
            throw new RuntimeException("Plays not found.");
        }
        return playRepository.findOpenByCafeId(cafeId);
    }

    public List<Play> findOpenPlaysByBoardGameId(Long boardGameId) {
        updatePlayStatusToPast();
        if (playRepository.findOpenByBoardGameId(boardGameId).isEmpty()) {
            throw new RuntimeException("Plays not found.");
        }
        return playRepository.findOpenByBoardGameId(boardGameId);
    }

    public List<Play> findOpenPlaysByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName) {
        updatePlayStatusToPast();
        if (playRepository.findOpenByBoardGameTitleAndPublisherName(boardGameTitle, publisherName).isEmpty()) {
            throw new RuntimeException("Plays not found.");
        }
        return playRepository.findOpenByBoardGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    public List<Play> findOpenPlaysByFreePlacesGreaterThanEqual(Integer peopleCount) {
        updatePlayStatusToPast();
        if (playRepository.findOpenByFreePlacesGreaterThanEqual(peopleCount).isEmpty()) {
            throw new RuntimeException("Plays not found.");
        }
        return playRepository.findOpenByFreePlacesGreaterThanEqual(peopleCount);
    }

    public List<Play> findOpenPlays() {
        updatePlayStatusToPast();
        if (playRepository.findOpen().isEmpty()) {
            throw new RuntimeException("Open plays not found.");
        }
        return playRepository.findOpen();
    }

    // updating methods
    public void updatePlayStatusToPast() {
        playRepository.updateStatusToPast(LocalDateTime.now());
    }

    public void updatePlayUsersAdd(User user, Long id) {
        Play play = playRepository.findById(id).orElseThrow(() -> new RuntimeException("Play not found."));
        if (!play.getUsers().contains(user)) {
            if (!play.getStatus().equals(Status.OPEN) || play.getFreePlaces() == 0) {
                throw new RuntimeException("Cannot enroll for the play.");
            }
            play.getUsers().add(user);
            play.setFreePlaces(play.getFreePlaces() - 1);
            if (play.getFreePlaces() == 0) {
                play.setStatus(Status.CLOSED);
            }
            playRepository.save(play);
        } else {
            throw new RuntimeException("User already enrolled for the play.");
        }
    }

    public void updatePlayUsersRemove(User user, Long id) {
        Play play = playRepository.findById(id).orElseThrow(() -> new RuntimeException("Play not found."));
        if (play.getUsers().contains(user)) {
            if (play.getStatus().equals(Status.PAST)) {
                throw new RuntimeException("Play has already been held.");
            } else if (play.getStatus().equals(Status.CLOSED)) {
                play.setStatus(Status.OPEN);
            }
            play.getUsers().remove(user);
            play.setFreePlaces(play.getFreePlaces() + 1);
            playRepository.save(play);
            if (play.getUsers().isEmpty()) {
                playRepository.deleteById(play.getId());
            }
        } else {
            throw new RuntimeException("User is not enrolled for the play.");
        }
    }
}
