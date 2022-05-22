package gr.accepted.gamemanagement.persistence;

import gr.accepted.gamemanagement.model.MatchOdd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A database repository of a {@link gr.accepted.gamemanagement.model.MatchOdd}
 */
@Repository
@Transactional
public interface MatchOddRepository extends JpaRepository<MatchOdd, Long> {

    @Modifying
    @Query("DELETE FROM MatchOdd mo where mo.id = :id")
    void delete(Long id);

}
