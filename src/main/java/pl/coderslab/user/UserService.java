package pl.coderslab.user;

import org.springframework.stereotype.Service;
import pl.coderslab.Role;
import pl.coderslab.boardgame.BoardGame;
import java.util.List;

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

    public boolean loginUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            String hashedPassword = userRepository.findByUsername(username).get().getPassword();
            return BCrypt.checkpw(password, hashedPassword);
        } else {
            return false;
        }
    }

    public void createUser(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        userRepository.save(user);
    }

    public User readUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
    }

    public void updateUser(User user) {
        User originalUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new RuntimeException("User not found."));
        originalUser.setUsername(user.getUsername());
        originalUser.setEmail(user.getEmail());
        originalUser.setPassword(hashPassword(user.getPassword()));
        originalUser.setRole(user.getRole());
        originalUser.setFavouriteGames(user.getFavouriteGames());
        originalUser.setWantedGames(user.getWantedGames());
        userRepository.save(originalUser);
    }

    public void deleteUserById(Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        userRepository.deleteById(id);
    }

    public List<User> readAllUsers() {
        if (userRepository.findAll().isEmpty()) {
            throw new RuntimeException("Users not found.");
        }
        return userRepository.findAll();
    }

    // finding methods
    public User readUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found."));
    }

    public List<User> findUsersByUsernameContaining(String username) {
        if (userRepository.findByUsernameContainingIgnoreCase(username).isEmpty()) {
            throw new RuntimeException("Users not found.");
        }
        return userRepository.findByUsernameContainingIgnoreCase(username);
    }

    List<User> findUsersByFavouriteGameId(Long favouriteGameId) {
        if (userRepository.findByFavouriteGameId(favouriteGameId).isEmpty()) {
            throw new RuntimeException("Users not found.");
        }
        return userRepository.findByFavouriteGameId(favouriteGameId);
    }

    List<User> findUsersByRole(Role role) {
        if (userRepository.findByRole(role).isEmpty()) {
            throw new RuntimeException("Users not found.");
        }
        return userRepository.findByRole(role);
    }

    // updating methods
    void updateUserUsername(String username, Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        userRepository.updateUsername(username, id);
    }

    void updateUserPassword(String password, Long id) {
        if (userRepository.findById(id).isEmpty()) {
            throw new RuntimeException("User not found.");
        }
        userRepository.updatePassword(hashPassword(password), id);
    }

    void updateUserFavouriteGamesAdd(BoardGame boardGame, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
        if (!user.getFavouriteGames().contains(boardGame)) {
            user.getFavouriteGames().add(boardGame);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Board game already on favourite list.");
        }
    }

    void updateUserFavouriteGamesRemove(BoardGame boardGame, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
        if (user.getFavouriteGames().contains(boardGame)) {
            user.getFavouriteGames().remove(boardGame);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Board game is not on favourite list.");
        }
    }

    void updateUserWantedGamesAdd(BoardGame boardGame, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
        if (!user.getWantedGames().contains(boardGame)) {
            user.getWantedGames().add(boardGame);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Board game already on wanted list.");
        }
    }

    void updateUserWantedGamesRemove(BoardGame boardGame, Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found."));
        if (!user.getWantedGames().contains(boardGame)) {
            user.getWantedGames().add(boardGame);
            userRepository.save(user);
        } else {
            throw new RuntimeException("Board game is not on wanted list.");
        }
    }
}

