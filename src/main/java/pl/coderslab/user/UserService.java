package pl.coderslab.user;

import org.springframework.stereotype.Service;
import pl.coderslab.Role;
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
    Optional<List<Cafe>> findUsersByFavouriteGameTitleAndPublisherName(String boardGameTitle, String publisherName) {
        return userRepository.findByFavouriteGameTitleAndPublisherName(boardGameTitle, publisherName);
    }

    void updateUsername(Long id, String username) {
        userRepository.updateUsername(id, username);
    };

    void updateEmail(Long id, String email) {
        userRepository.updateEmail(id, email);
    };
    void updatePassword(Long id, String password) {
        userRepository.updatePassword(id, password);
    };

    void updateRole(Long id, Role role) {
        userRepository.updateRole(id, role);
    };

    void updateFavouriteGamesAddBoardGameById(Long id, Long boardGameId) {
        userRepository.updateFavouriteGamesAddBoardGameById(id, boardGameId);
    };
    void updateFavouriteGamesRemoveBoardGameById(Long id, Long boardGameId) {
        userRepository.updateFavouriteGamesRemoveBoardGameById(id, boardGameId);
    };
}

