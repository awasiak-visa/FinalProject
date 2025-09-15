package pl.coderslab.user;

import org.springframework.stereotype.Service;
import pl.coderslab.Role;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.cafe.Cafe;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        userRepository.save(user);
    }

    public Optional<User> readUserById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User> readUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void updateUser(User user) {
        userRepository.update(user.getUsername(), user.getEmail(), user.getPassword(), user.getRole(),
                user.getFavouriteGames(), user.getWantedGames(), user.getId());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
    public void deleteUserByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public List<User> readAllUsers() {
        return userRepository.findAll();
    }

    Optional<List<User>> findUsersByFavouriteGameId(Long id) {
        return userRepository.findByFavouriteGameId(id);
    }
    Optional<List<User>> findUsersByFavouriteGameTitleAndPublisherName(String boardGameTitle, String publisherName) {
        return userRepository.findByFavouriteGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    void updateUsername(String username, Long id) {
        userRepository.updateUsername(username, id);
    };

    void updateEmail(String email, Long id) {
        userRepository.updateEmail(email, id);
    };
    void updatePassword(String password, Long id) {
        userRepository.updatePassword(password, id);
    };

    void updateRole(Role role, Long id) {
        userRepository.updateRole(role, id);
    };

    void updateFavouriteGamesAddBoardGame(BoardGame boardGame, Long id) {
        List<BoardGame> favouriteGames = userRepository.findById(id).get().getFavouriteGames();
        favouriteGames.add(boardGame);
        userRepository.updateFavouriteGames(favouriteGames, id);
    };
    void updateFavouriteGamesRemoveBoardGame(BoardGame boardGame, Long id) {
        List<BoardGame> favouriteGames = userRepository.findById(id).get().getFavouriteGames();
        favouriteGames.remove(boardGame);
        userRepository.updateFavouriteGames(favouriteGames, id);
    };

    void updateWantedGamesAddBoardGame(BoardGame boardGame, Long id) {
        List<BoardGame> wantedGames = userRepository.findById(id).get().getWantedGames();
        wantedGames.add(boardGame);
        userRepository.updateWantedGames(wantedGames, id);
    };
    void updateWantedGamesRemoveBoardGame(BoardGame boardGame, Long id) {
        List<BoardGame> wantedGames = userRepository.findById(id).get().getWantedGames();
        wantedGames.remove(boardGame);
        userRepository.updateWantedGames(wantedGames, id);
    };
}

