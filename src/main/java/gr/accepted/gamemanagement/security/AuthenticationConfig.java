package gr.accepted.gamemanagement.security;

import gr.accepted.gamemanagement.persistence.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Authentication configuration
 */
@Configuration
public class AuthenticationConfig {

    @Bean
    public BasicAuthenticationProvider basicAuthenticationProvider(UserRepository userRepository){
        return new BasicAuthenticationProvider(userRepository);
    }

}
