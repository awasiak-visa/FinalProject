package pl.coderslab.play;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PlayRepository extends JpaRepository<Play, Long> {

    // finding queries
    @Query("select p from Play p join p.users u where u.id=?1")
    List<Play> findByUserId(Long userId);

    @Query("select p from Play p join p.cafe c where c.id=?1 and p.status=0")
    List<Play> findOpenByCafeId(Long cafeId);

    @Query("select p from Play p join p.boardGame b where b.id=?1 and p.status=0")
    List<Play> findOpenByBoardGameId(Long boardGameId);

    @Query("select p from Play p join p.boardGame b where b.title=?1 and b.publisher.name=?2 and p.status=0")
    List<Play> findOpenByBoardGameTitleAndPublisherName(String boardGameTitle, String publisherName);

    @Query("select p from Play p where p.freePlaces>=:peopleCount and p.status=0")
    List<Play> findOpenByFreePlacesGreaterThanEqual(Integer peopleCount);

    @Query("select p from Play p where p.status=0")
    List<Play> findOpen();

    // updating queries
    @Modifying
    @Transactional
    @Query("update Play p set p.status=2 where p.dateTime<:now")
    void updateStatusToPast(LocalDateTime now);
}
