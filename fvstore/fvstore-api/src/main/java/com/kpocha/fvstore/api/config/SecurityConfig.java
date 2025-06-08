package com.kpocha.fvstore.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable()) // Deshabilitar CSRF para APIs stateless si se usa JWT
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/productos/**", "/swagger-ui/**", "/api-docs/**").permitAll() // Endpoints de productos y swagger públicos por ahora
                .requestMatchers("/api/v1/auth/**").permitAll() // Endpoints de autenticación públicos
                .anyRequest().authenticated() // Todos los demás requieren autenticación
            )
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Usar sesiones stateless con JWT

        // Aquí se añadiría el filtro para JWT
        // http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    // Ejemplo de cómo se podría definir el bean del filtro JWT (requiere más implementación)
    /*
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        // return new JwtAuthenticationFilter(jwtTokenProvider, userDetailsService);
        return null; // Placeholder
    }
    */
}
