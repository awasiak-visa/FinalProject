package pl.coderslab.boardgame;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    Optional<Publisher> findByName(String name);

    @Modifying
    @Transactional
    @Query("update Publisher p set p.name=?1 where p.id=?2")
    void update(String name, Long id);

    void deleteByName(String name);
}
