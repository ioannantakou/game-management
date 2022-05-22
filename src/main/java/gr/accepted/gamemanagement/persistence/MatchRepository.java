package gr.accepted.gamemanagement.persistence;

import gr.accepted.gamemanagement.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * A database Repository of a {@link Match}
 */
@Repository
@Transactional
public interface MatchRepository extends JpaRepository<Match, Long> {
}
