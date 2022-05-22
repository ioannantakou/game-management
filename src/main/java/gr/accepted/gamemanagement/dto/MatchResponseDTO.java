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

    private Long id;

    private String description;

    private List<MatchOddResponseDTO> matchOdds;

    private LocalDate date;

    private LocalTime time;

    private String teamA;

    private String teamB;

    private Sport sport;
}
