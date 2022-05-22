package gr.accepted.gamemanagement.security;

import gr.accepted.gamemanagement.model.User;
import gr.accepted.gamemanagement.persistence.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Implementation of a basic authentication
 */
@Slf4j
public class BasicAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;

    public BasicAuthenticationProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String principal = (String)authentication.getPrincipal();
        final String password = (String)authentication.getCredentials();
        if (principal.isBlank() || password.isBlank()){
            throw new BadCredentialsException("Illegal username or password");
        }
        log.info("Authentication user: "+principal);
        User user = userRepository.find(principal, password).orElseThrow(()->new UsernameNotFoundException("Illegal username password"));
        final UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(result);
        return result;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
