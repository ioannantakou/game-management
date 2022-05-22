package gr.accepted.gamemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * Match odd input for inserting/modifying {@link gr.accepted.gamemanagement.model.MatchOdd}
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchOddInputDTO {

    @NotEmpty(message = "specifier must not be null or empty")
    private String specifier;

    @NotEmpty(message = "odd must not be null or empty")
    private double odd;

}
