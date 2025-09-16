package pl.coderslab.user;

import org.springframework.stereotype.Service;
import pl.coderslab.Role;
import pl.coderslab.boardgame.BoardGame;
import java.util.List;
import java.util.Optional;
import org.mindrot.jbcrypt.BCrypt;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public void createUser(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
    }

    public Optional<User> readUserById(Long id) {
        return userRepository.findById(id);
    }

    public void updateUser(User user) {
        userRepository.update(user.getUsername(), user.getEmail(), hashPassword(user.getPassword()), user.getRole(),
                user.getFavouriteGames(), user.getWantedGames(), user.getId());
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> readAllUsers() {
        return userRepository.findAll();
    }

    // finding methods
    public Optional<List<User>> findUsersByUsernameContaining(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    Optional<List<User>> findUsersByFavouriteGameId(Long favouriteGameId) {
        return userRepository.findByFavouriteGameId(favouriteGameId);
    }

    Optional<List<User>> findUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    // updating methods
    void updateUserUsername(String username, Long id) {
        userRepository.updateUsername(username, id);
    }

    void updateUserPassword(String password, Long id) {
        userRepository.updatePassword(hashPassword(password), id);
    }

    void updateUserFavouriteGamesAdd(BoardGame boardGame, Long id) {
        if (userRepository.findById(id).isPresent()) {
            List<BoardGame> favouriteGames = userRepository.findById(id).get().getFavouriteGames();
            favouriteGames.add(boardGame);
            userRepository.updateFavouriteGames(favouriteGames, id);
        }
    }

    void updateUserFavouriteGamesRemove(BoardGame boardGame, Long id) {
        if (userRepository.findById(id).isPresent()) {
            List<BoardGame> favouriteGames = userRepository.findById(id).get().getFavouriteGames();
            favouriteGames.remove(boardGame);
            userRepository.updateFavouriteGames(favouriteGames, id);
        }
    }

    void updateUserWantedGamesAdd(BoardGame boardGame, Long id) {
        if (userRepository.findById(id).isPresent()) {
            List<BoardGame> wantedGames = userRepository.findById(id).get().getWantedGames();
            wantedGames.add(boardGame);
            userRepository.updateWantedGames(wantedGames, id);
        }
    }

    void updateUserWantedGamesRemove(BoardGame boardGame, Long id) {
        if (userRepository.findById(id).isPresent()) {
            List<BoardGame> wantedGames = userRepository.findById(id).get().getWantedGames();
            wantedGames.remove(boardGame);
            userRepository.updateWantedGames(wantedGames, id);
        }
    }
}

