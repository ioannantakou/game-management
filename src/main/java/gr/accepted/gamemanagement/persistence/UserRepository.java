package gr.accepted.gamemanagement.persistence;

import gr.accepted.gamemanagement.model.User;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * User Repository to retrieve users as {@link User}.
 */
@Component
public class UserRepository {

    /**
     * TODO implement user verification after searching in database/authentication server etc.
     */
    private static final Map<String, User> users;
    static {
        users = Map.ofEntries(Map.entry("ioanna", new User("11111", "ioanna")),
                Map.entry("john", new User("22222", "john")),
                Map.entry("maria", new User("33333", "maria")));

    }

    public Optional<User> find(String username, String password){
        return Optional.ofNullable(users.get(username));
    }

}
