package pl.coderslab.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.boardgame.BoardGame;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Modifying
    @Transactional
    @Query("update Review r set r.boardGame=?1, r.title=?2, r.description=?3 where r.id=?4")
    void update(BoardGame boardGame, String title, String description, Long id);

    Optional<List<Review>> findByBoardGameId(Long id);
    @Query("select r from Review r join r.boardGame b where b.title=?1 and b.publisher.name=?2")
    Optional<List<Review>> findByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName);

    Optional<List<Review>> findByUserId(Long id);
    Optional<List<Review>> findByUserUsername(String username);
}
