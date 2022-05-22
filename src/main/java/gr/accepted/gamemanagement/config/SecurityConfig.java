package gr.accepted.gamemanagement.config;

import gr.accepted.gamemanagement.security.AuthenticationConfig;
import gr.accepted.gamemanagement.security.BasicAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Security configuration application.
 */
@EnableWebSecurity
@Configuration
@Import(AuthenticationConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final BasicAuthenticationProvider basicAuthenticationProvider;

    public SecurityConfig(BasicAuthenticationProvider basicAuthenticationProvider) {
        this.basicAuthenticationProvider = basicAuthenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(basicAuthenticationProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authenticationProvider(basicAuthenticationProvider)
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .anyRequest().authenticated()
                .and().csrf().disable().httpBasic();
    }

}
