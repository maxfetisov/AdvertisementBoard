package com.advertisementboard.configuration;

import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "/api/account/**",
                        "/home",
                        "/advertisements",
                        "/createAdvertisement",
                        "/js/**",
                        "/css/**",
                        "/image/**",
                        "/api/contacts",
                        "/api/advertisements/filter",
                        "/api/advertisements/*",
                        "/api/advertisements",
                        "/favicon.ico",
                        "/webjars/jquery/3.6.4/jquery.min.js"
                ).permitAll()
                .requestMatchers(HttpMethod.PUT, "/api/advertisements/*/**")
                .hasAnyAuthority(UserRole.ADMINISTRATOR.name(), UserRole.MODERATOR.name())
                .requestMatchers(HttpMethod.GET, "/api/categories")
                .permitAll()
                .requestMatchers(HttpMethod.POST, "/api/categories")
                .hasAuthority(UserRole.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.PUT, "/api/categories")
                .hasAuthority(UserRole.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.DELETE, "/api/categories/*")
                .hasAuthority(UserRole.ADMINISTRATOR.name())
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration)
            throws Exception {
        return configuration.getAuthenticationManager();
    }

}
