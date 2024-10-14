package com.example.coffeeshop.config;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.List;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final List<String> AUTH_WHITE_LIST = List.of(
            "/actuator/**", "/configuration/**",
            "/v2/api-docs", "/v3/api-docs/**",
            "/swagger-ui/**", "/swagger*/**", "/webjars/**"
    );

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AntPathRequestMatcher[] authWhiteListAntPathRequestMatchers = AUTH_WHITE_LIST.stream()
                .map(AntPathRequestMatcher::antMatcher)
                .toArray(AntPathRequestMatcher[]::new);
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(authWhiteListAntPathRequestMatchers).permitAll()
//                        .requestMatchers(AntPathRequestMatcher.antMatcher("/api/v1/**")).permitAll()
                        .anyRequest().authenticated()
                );

        http
                //for simple implement, use basic authentication
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(customAuthProvider());

        return http.build();
    }

    private AuthenticationProvider customAuthProvider() {
        return new AuthenticationProvider() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String username = authentication.getName();
                String password = authentication.getCredentials().toString();

                if ("admin".equals(username) && "admin".equals(password)) {
                    return new UsernamePasswordAuthenticationToken(username, password,
                            List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
                } else {
                    throw new BadCredentialsException("Invalid username or password");
                }
            }

            @Override
            public boolean supports(Class<?> authentication) {
                return true;
            }
        };
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }
}
