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

    // finding queries
    @Query("select p from Play p join p.users u where u.id=?1")
    Optional<List<Play>> findByUserId(Long id);

    @Query("select p from Play p join p.cafe c where c.id=?1 and p.status=0")
    Optional<List<Play>> findOpenByCafeId(Long id);

    @Query("select p from Play p join p.boardGame b where b.id=?1 and p.status=0")
    Optional<List<Play>> findOpenByBoardGameId(Long id);

    @Query("select p from Play p join p.boardGame b where b.title=?1 and b.publisher.name=?2 and p.status=0")
    Optional<List<Play>> findOpenByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName);

    @Query("select p from Play p where p.freePlaces>=:peopleCount and p.status=0")
    Optional<List<Play>> findOpenByFreePlacesGreaterThanEqual(Integer peopleCount);

    @Query("select p from Play p where p.status=0")
    Optional<List<Play>> findOpen();

    // updating queries
    @Modifying
    @Transactional
    @Query("update Play p set p.status=?1 where p.id=?2")
    void updateStatus(Status status, Long id);

    @Modifying
    @Transactional
    @Query("update Play p set p.users=?1 where p.id=?2")
    void updateUsers(List<User> users, Long id);

    @Modifying
    @Transactional
    @Query("update Play p set p.dateTime=?1 where p.id=?2")
    void updateDateTime(LocalDateTime dateTime, Long id);

    @Modifying
    @Transactional
    @Query("update Play p set p.freePlaces=?1 where p.id=?2")
    void updateFreePlaces(Integer freePlaces, Long id);
}
