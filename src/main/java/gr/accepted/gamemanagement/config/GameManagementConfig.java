package gr.accepted.gamemanagement.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * General configuration for game management application
 */
@Configuration
public class GameManagementConfig {

    /**
     * Model mapper used for Request/Response objects
     * @return {@link ModelMapper}
     */
    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }

}
