package pl.coderslab.play;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.Status;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.cafe.Cafe;
import pl.coderslab.user.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {

    @Modifying
    @Transactional
    @Query("update Play p set p.boardGame=?1, p.dateTime=?2, p.cafe=?3, p.users=?4, p.status=?5 where p.id=?6")
    void update(BoardGame boardGame, LocalDateTime dateTime, Cafe cafe, List<User> users, Status status, Long id);

    void updateStatus(Long id, Status status);

    void updateUsersAddUserById(Long id, Long userId);
    void updateUsersRemoveUserById(Long id, Long userId);

    @Query("select p from Play p join p.users u where u.id=?1")
    Optional<List<User>> findByUserId(Long id);

    @Query("select p from Play p join p.cafe c where c.id=?1 and p.status=?2")
    Optional<List<User>> findOpenByCafeId(Long id, Status OPEN);

    @Query("select p from Play p join p.boardGame b where b.id=?1 and p.status=?2")
    Optional<List<User>> findOpenByBoardGameId(Long id, Status OPEN);

    @Query("select p from Play p join p.boardGame b where b.title=?1 and b.publisher.name=?2 and p.status=?3")
    Optional<List<User>> findOpenByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName, Status OPEN);
}
