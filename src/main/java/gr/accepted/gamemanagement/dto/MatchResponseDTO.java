package gr.accepted.gamemanagement.dto;

import gr.accepted.gamemanagement.model.Sport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Response mapping of {@link gr.accepted.gamemanagement.model.Match}
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchResponseDTO {

    /**
     * The id
     */
    private Long id;

    /**
     * The description of the match
     */
    private String description;

    /**
     * A list of {@link MatchOddResponseDTO}
     */
    private List<MatchOddResponseDTO> matchOdds;

    /**
     * The date of the match
     */
    private LocalDate date;

    /**
     * The time of the match
     */
    private LocalTime time;

    /**
     * Team A of the match
     */
    private String teamA;

    /**
     * Team B of the match
     */
    private String teamB;

    /**
     * The sport related
     */
    private Sport sport;
}
