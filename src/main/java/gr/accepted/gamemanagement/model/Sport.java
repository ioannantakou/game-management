package gr.accepted.gamemanagement.model;

/**
 * Different sports supported in the application
 */
public enum Sport {

    BASKETBALL(1),

    FOOTBALL(2);

    public final Integer code;

    Sport(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

}
