package pl.coderslab.boardgame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.Difficulty;
import pl.coderslab.cafe.Cafe;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

    @Modifying
    @Transactional
    @Query("update BoardGame b set b.title=?1, b.publisher=?2, b.description=?3, b.minPlayerCount=?4, " +
            "b.minPlayerCount=?5, b.minTime=?6, b.maxTime=?7, b.difficulty=?8, b.categories=?9, b.rating=?10 " +
            "where b.id=?11")
    void update(String title, Publisher publisher, String description, Integer minPlayerCount, Integer maxPlayerCount,
                Integer minTime, Integer maxTime, Difficulty difficulty, List<Category> categories, Double rating,
                Long id);


    // finding queries
    Optional<List<BoardGame>> findByTitle(String title);
    Optional<List<BoardGame>> findByPublisherName(String publisherName);
    Optional<List<BoardGame>> findByDifficulty(Difficulty difficulty);
    Optional<List<BoardGame>> findByRatingGreaterThanEqual(Double rating);

    @Query("select b from BoardGame b where b.minPlayerCount<=:playerCount and b.maxPlayerCount>=:playerCount")
    Optional<List<BoardGame>> findByPlayerCountBetweenMinAndMaxPlayerCount(Integer playerCount);

    Optional<List<BoardGame>> findByMaxTimeLessThanEqual(Integer maxTime);
    @Query("select b from BoardGame b where b.minTime<=:time and b.maxTime>=:time")
    Optional<List<BoardGame>> findByTimeBetweenMinAndMaxTime(Integer time);

    @Query("select b from BoardGame b join b.categories c where c.name=?1")
    Optional<List<BoardGame>> findByCategoryName(String categoryNames);

    // updating queries
    @Modifying
    @Transactional
    @Query("update BoardGame b set b.description=?1 where b.id=?2")
    void updateDescription(String description, Long id);

    @Modifying
    @Transactional
    @Query("update BoardGame b set b.categories=?1 where b.id=?2")
    void updateCategories(List<Category> categories, Long id);

    @Modifying
    @Transactional
    @Query("update BoardGame b set b.rating=?1 where b.id=?2")
    void updateRating(Double rating, Long id);

}
