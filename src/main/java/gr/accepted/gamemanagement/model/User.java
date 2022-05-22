package gr.accepted.gamemanagement.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * This object describes an authenticated user
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
public class User {

    /**
     * The code of the user in db
     */
    private String code;

    /**
     * The username of the user
     */
    private String username;

    /** TODO
     * add authorities/permissions related to user
     */

}
