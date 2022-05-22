package gr.accepted.gamemanagement.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * General configuration for game management application
 */
@Configuration
public class GameManagementConfig {

    @Bean
    ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
