package com.eldar.configs.secuirity;

import com.eldar.api.exceptions.customs.NotFoundException;
import com.eldar.persistence.repository.UserRepository;
import com.eldar.utils.constants.UserConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

/**
 * Class to inject the PasswordEncoder
 * @author  caito Vilas
 * date 08/2021
 */
@Configuration
@RequiredArgsConstructor
public class BeansInyectorConfig {
    private final UserRepository userRepository;

    /**
     * Method to inject the PasswordEncoder
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * Method to inject the AuthenticationProvider
     * @return AuthenticationProvider
     */
    @Bean
    public AuthenticationProvider authenticationProvider(){
        var provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Method to inject the UserDetailsService
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return (user) -> userRepository.findByEmail(user).orElseThrow(()->
                new NotFoundException(UserConstants.USER_NOT_FOUND));
    }

    /**
     * Method to inject the AuthenticationManager
     * @param configuration
     * @return AuthenticationManager
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
