package com.facilvirtual.fvstores.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Using PasswordEncoderFactories.createDelegatingPasswordEncoder() for demo purposes.
        // This will use bcrypt by default.
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        UserDetails user = User.builder()
            .username("user")
            .password(encoder.encode("password"))
            .roles("USER")
            .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                      .mvcMatchers("/api/**").authenticated() // Secure all /api/** endpoints
                .anyRequest().permitAll() // Allow other requests (e.g. to actuator health if not under /api)
            )
            .csrf(csrf -> csrf.disable()) // Disable CSRF for stateless API, or configure properly if using sessions
            .httpBasic(withDefaults()); // Enable HTTP Basic authentication
        return http.build();
    }
}
