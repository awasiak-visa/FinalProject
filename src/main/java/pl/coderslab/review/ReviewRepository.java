package pl.coderslab.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.boardgame.BoardGame;
import pl.coderslab.user.User;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying
    @Transactional
    @Query("update Review r set r.boardGame=?1, r.rating=?2, r.title=?3, r.description=?4, r.user=?5 where r.id=?6")
    void update(BoardGame boardGame, Integer rating, String title, String description, User user, Long id);

    // finding queries
    Optional<List<Review>> findByBoardGameId(Long boardGameId);

    @Query("select r from Review r join r.boardGame b where b.title=?1 and b.publisher.name=?2")
    Optional<List<Review>> findByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName);

    Optional<List<Review>> findByUserId(Long userId);

    @Query("select avg(r.rating) from Review r join r.boardGame b where b.id=?1")
    Double findAverageRatingByBoardGameId(Long boardGameId);

    // updating queries
    @Modifying
    @Transactional
    @Query("update Review r set r.rating=?1 where r.id=?2")
    void updateRating(Integer rating, Long id);

    @Modifying
    @Transactional
    @Query("update Review r set r.title=?1 where r.id=?2")
    void updateTitle(String title, Long id);

    @Modifying
    @Transactional
    @Query("update Review r set r.description=?1 where r.id=?2")
    void updateDescription(String description, Long id);
}
