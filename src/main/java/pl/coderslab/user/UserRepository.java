package pl.coderslab.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.Role;
import pl.coderslab.boardgame.BoardGame;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.username=?1, u.email=?2, u.password=?3, u.role=?4, u.favouriteGames=?5, " +
            "u.wantedGames=?6 where u.id=?7")
    void update(String username, String email, String password, Role role, List<BoardGame> favouriteGames,
                List<BoardGame> wantedGames, Long id);

    void deleteByUsername(String username);

    @Query("select u from User u join u.favouriteGames f where f.id=?1")
    Optional<List<User>> findByFavouriteGameId(Long id);
    @Query("select u from User u join u.favouriteGames f where f.title=?1 and f.publisher.name=?2")
    Optional<List<User>> findByFavouriteGameTitleAndPublisherName(String boardGameTitle, String publisherName);

    @Modifying
    @Transactional
    @Query("update User u set u.username=?1 where u.id=?2")
    void updateUsername(String username, Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.email=?1 where u.id=?2")
    void updateEmail(String email, Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.password=?1 where u.id=?2")
    void updatePassword(String password, Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.role=?1 where u.id=?2")
    void updateRole(Role role, Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.favouriteGames=?1 where u.id=?2")
    void updateFavouriteGames(List<BoardGame> favouriteGames, Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.wantedGames=?1 where u.id=?2")
    void updateWantedGames(List<BoardGame> wantedGames, Long id);
}
