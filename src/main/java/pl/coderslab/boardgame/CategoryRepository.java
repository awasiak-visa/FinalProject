package pl.coderslab.boardgame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Modifying
    @Transactional
    @Query("update Category c set c.name=?1 where c.id=?2")
    void update(String name, Long id);

    void deleteByName(String name);
}
