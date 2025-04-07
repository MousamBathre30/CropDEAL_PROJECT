package com.gateway.ApiGateWay.config;

import com.gateway.ApiGateWay.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthenticationFilter authenticationFilter) {
        http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(
                                "/auth/login",
                                "/auth/register",
                                "/auth/**",
                                "/v3/api-docs/**",
                                "/webjars/swagger-ui/**",
                                "/swagger-ui.html*"
                        ).permitAll()
                        .pathMatchers("/api/crops/**").hasRole("ROLE_FARMER")
                        .pathMatchers("/api/dealer/**", "/api/payment/**").hasRole("ROLE_DEALER")
                        .pathMatchers("/api/users/**").hasRole("ROLE_ADMIN")
                        .anyExchange().authenticated()
                )
                .addFilterBefore(authenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION);

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
