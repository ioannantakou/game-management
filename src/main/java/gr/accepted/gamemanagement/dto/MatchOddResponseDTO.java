package gr.accepted.gamemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Response mapping of a {@link gr.accepted.gamemanagement.model.MatchOdd}
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MatchOddResponseDTO {

    /**
     * The id of the match odd
     */
    private Long id;

    /**
     * The specifier of the match odd
     */
    private String specifier;

    /**
     * The match odd value
     */
    private double odd;
}
