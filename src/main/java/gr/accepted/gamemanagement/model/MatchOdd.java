package gr.accepted.gamemanagement.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Persistence object representing a match odd
 */
@Entity
@Table(name="matchodds")
@Setter
@Getter
public class MatchOdd {

    /**
     * The id of the match odd
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The match referring to this odd
     */
    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;

    /**
     * The specifier of the match odd
     */
    @Column(name = "specifier")
    private String specifier;

    /**
     * The odd number
     */
    @Column(name = "odd")
    private double odd;

}
