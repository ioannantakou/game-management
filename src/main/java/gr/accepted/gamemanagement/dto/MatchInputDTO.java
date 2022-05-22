package gr.accepted.gamemanagement.dto;

import gr.accepted.gamemanagement.model.Sport;
import gr.accepted.gamemanagement.validators.ValueOfEnum;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Match input dto for creating and modifying {@link gr.accepted.gamemanagement.model.Match}
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MatchInputDTO {

    /**
     * The description of the match
     */
    private String description;

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
    @NotEmpty(message = "teamA must not be null or empty")
    private String teamA;

    /**
     * Team B of the match
     */
    @NotEmpty(message = "teamB must not be null or empty")
    private String teamB;

    /**
     * The related sport
     */
    @NotEmpty(message = "sport must not be null or empty")
    @ValueOfEnum(enumClass = Sport.class, message = "sport not supported")
    private String sport;

}
