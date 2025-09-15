package pl.coderslab.cafe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.boardgame.BoardGame;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CafeRepository extends JpaRepository<Cafe, Long> {

    @Modifying
    @Transactional
    @Query("update Cafe c set c.name=?1, c.openingTime=?2, c.closingTime=?3, c.address=?4, c.boardGames=?5 " +
            "where c.id=?9")
    void update(String name, LocalTime openingTime, LocalTime closingTime, String address, List<BoardGame> boardGames,
                Long id);

    Optional<List<Cafe>> findByName(String name);

    @Query("select c from Cafe c join c.boardGames b where b.id=?1")
    Optional<List<Cafe>> findByBoardGameId(Long id);
    @Query("select c from Cafe c join c.boardGames b where b.title=?1 and b.publisher.name=?2")
    Optional<List<Cafe>> findByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName);

    Optional<List<Cafe>> findByOpeningTimeIsBefore(LocalTime openingTime);
    Optional<List<Cafe>> findByClosingTimeIsAfter(LocalTime closingTime);

    Optional<List<Cafe>> findByAddressContainingIgnoreCase(String address);

    @Modifying
    @Transactional
    @Query("update Cafe c set c.address=?1 where c.id=?2")
    void updateAddress(String address, Long id);

    @Modifying
    @Transactional
    @Query("update Cafe c set c.name=?1 where c.id=?2")
    void updateName(String name, Long id);

    @Modifying
    @Transactional
    @Query("update Cafe c set c.openingTime=?1, c.closingTime=?2 where c.id=?3")
    void updateOpeningTimeAndClosingTime(LocalTime openingTime, LocalTime closingTime, Long id);

    @Modifying
    @Transactional
    @Query("update Cafe c set c.boardGames=?1 where c.id=?2")
    void updateBoardGames(List<BoardGame> boardGames, Long id);
}
