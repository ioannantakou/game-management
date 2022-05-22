package gr.accepted.gamemanagement.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

/**
 * Persistence object representing a match
 */
@Entity
@Table(name = "match")
@Setter
@Getter
@NoArgsConstructor
public class Match {

    /**
     * the id of the match
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * a list of {@link MatchOdd} of the match
     */
    @OneToMany(mappedBy = "match", fetch = FetchType.EAGER, cascade=CascadeType.ALL)
    private Set<MatchOdd> matchOdds; //owning side

    /**
     * A description of a match
     */
    @Column(name = "description")
    private String description;

    /**
     * The date of the match
     */
    @Column(name = "match_date")
    private LocalDate date;

    /**
     * The time of the match
     */
    @Column(name = "match_time")
    private LocalTime time;

    /**
     * Team A of the match
     */
    @Column(name = "team_a")
    private String teamA;

    /**
     * Team B of the match
     */
    @Column(name = "team_b")
    private String teamB;

    /**
     * The {@link Sport} of the match
     */
    @Column(name = "sport")
    private Sport sport;

}
