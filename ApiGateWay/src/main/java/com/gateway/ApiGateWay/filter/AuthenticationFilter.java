package com.gateway.ApiGateWay.filter;

import com.gateway.ApiGateWay.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class AuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Autowired
    public AuthenticationFilter(JwtUtil jwtUtil) {
        System.out.println("🛡️ AuthenticationFilter initialized");
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getURI().getPath();

        if (isPublicEndpoint(path)) {
            return chain.filter(exchange);
        }

        String authHeader = request.getHeaders().getFirst("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return unauthorized(exchange, "Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);

        try {
            String username = jwtUtil.extractUsername(token);
            String role = jwtUtil.extractRole(token);

            if (!jwtUtil.isTokenValid(token, username)) {
                return unauthorized(exchange, "Invalid token");
            }

            if (path.startsWith("/api/crops") && !role.equalsIgnoreCase("ROLE_FARMER")) {
                return unauthorized(exchange, "Only FARMER can access Crop Service");
            } else if ((path.startsWith("/api/order") || path.startsWith("/api/payment"))
                    && !role.equalsIgnoreCase("DEALER")) {
                return unauthorized(exchange, "Only DEALER can access Order/Payment Services");
            }else if ((path.startsWith("/api/users"))
                    && !role.equalsIgnoreCase("ADMIN")) {
                return unauthorized(exchange, "Only ADMIN can access Order/Payment Services");
            }

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username, null, Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role)));

            SecurityContextImpl securityContext = new SecurityContextImpl(authToken);

            System.out.println("✅ JWT validated");
            System.out.println("➡ Username: " + username);
            System.out.println("➡ Role: " + role);
            System.out.println("➡ Path: " + path);

            return chain.filter(exchange)
                    .contextWrite(ReactiveSecurityContextHolder.withSecurityContext(Mono.just(securityContext)));

        } catch (Exception e) {
            System.out.println("❌ JWT Exception: " + e.getMessage());
            return unauthorized(exchange, "Token processing error");
        }
    }

    private boolean isPublicEndpoint(String path) {
        return path.startsWith("/auth/login") ||
                path.startsWith("/auth/register") ||
                path.startsWith("/auth/refresh") ||
                path.startsWith("/actuator") ||
                path.startsWith("/swagger") ||
                path.startsWith("/v3/api-docs");
    }


    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        System.out.println("⛔ Unauthorized: " + message);
        return exchange.getResponse().setComplete();
    }
}
