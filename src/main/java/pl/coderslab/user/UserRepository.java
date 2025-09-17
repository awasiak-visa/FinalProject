package pl.coderslab.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.Role;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // finding queries
    Optional<User> findByUsername(String username);

    List<User> findByUsernameContainingIgnoreCase(String username);

    List<User> findByRole(Role role);

    @Query("select u from User u join u.favouriteGames f where f.id=?1")
    List<User> findByFavouriteGameId(Long favouriteGameId);

    // updating queries
    @Modifying
    @Transactional
    @Query("update User u set u.username=?1 where u.id=?2")
    void updateUsername(String username, Long id);

    @Modifying
    @Transactional
    @Query("update User u set u.password=?1 where u.id=?2")
    void updatePassword(String password, Long id);
}
