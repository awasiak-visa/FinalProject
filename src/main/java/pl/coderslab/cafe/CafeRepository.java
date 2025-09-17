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

    // finding queries
    List<Cafe> findByName(String name);

    @Query("select c from Cafe c join c.boardGames b where b.id=?1")
    List<Cafe> findByBoardGameId(Long boardGameId);

    @Query("select c from Cafe c join c.boardGames b where b.title=?1 and b.publisher.name=?2")
    List<Cafe> findByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName);

    @Query("select c from Cafe c where c.openingTime<=:time and :time<=c.closingTime")
    List<Cafe> findByTimeBetweenOpeningAndClosingTime(LocalTime time);

    List<Cafe> findByAddressContainingIgnoreCase(String address);

    // updating queries
    @Modifying
    @Transactional
    @Query("update Cafe c set c.openingTime=?1 where c.id=?2")
    void updateOpeningTime(LocalTime openingTime, Long id);

    @Modifying
    @Transactional
    @Query("update Cafe c set c.closingTime=?1 where c.id=?2")
    void updateClosingTime(LocalTime closingTime, Long id);
}
