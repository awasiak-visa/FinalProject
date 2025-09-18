package pl.coderslab.boardgame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.Difficulty;
import java.util.List;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

    // finding queries
    List<BoardGame> findByTitle(String title);

    List<BoardGame> findByPublisherName(String publisherName);

    List<BoardGame> findByDifficulty(Difficulty difficulty);

    List<BoardGame> findByRatingGreaterThanEqual(Double rating);

    @Query("select b from BoardGame b where b.minPlayerCount<=:playerCount and b.maxPlayerCount>=:playerCount")
    List<BoardGame> findByPlayerCountBetweenMinAndMaxPlayerCount(Integer playerCount);

    List<BoardGame> findByMaxTimeLessThanEqual(Integer maxTime);

    @Query("select b from BoardGame b where b.minTime<=:time and b.maxTime>=:time")
    List<BoardGame> findByTimeBetweenMinAndMaxTime(Integer time);

    @Query("select b from BoardGame b join b.categories c where c.name=?1")
    List<BoardGame> findByCategoryName(String categoryNames);

    // updating queries
    @Modifying
    @Transactional
    @Query("update BoardGame b set b.description=?1 where b.id=?2")
    void updateDescription(String description, Long id);

    @Modifying
    @Transactional
    @Query("update BoardGame b set b.rating=?1 where b.id=?2")
    void updateRating(Double rating, Long id);
}
