package pl.coderslab.boardgame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.Difficulty;

import java.util.Set;

@Repository
public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {

    @Modifying
    @Transactional
    @Query("update BoardGame b set b.title=?1, b.publisher=?2, b.description=?3, b.playerCount=?4, b.time=?5, " +
            "b.difficulty=?6, b.categories=?7, b.rating=?8 where b.id=?9")
    void update(String title, Publisher publisher, String description, Integer playerCount, Integer time,
                Difficulty difficulty, Set<Category> categories, Double rating);
}
