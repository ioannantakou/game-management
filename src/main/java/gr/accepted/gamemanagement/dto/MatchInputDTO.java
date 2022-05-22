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

    private String description;

    private LocalDate date;

    private LocalTime time;

    @NotEmpty(message = "teamA must not be null or empty")
    private String teamA;

    @NotEmpty(message = "teamB must not be null or empty")
    private String teamB;

    @NotEmpty(message = "sport must not be null or empty")
    @ValueOfEnum(enumClass = Sport.class, message = "sport not supported")
    private String sport;

}
