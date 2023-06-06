package com.advertisementboard.configuration;

import com.advertisementboard.data.entity.Role;
import com.advertisementboard.data.enumeration.UserRole;
import com.advertisementboard.service.mapper.RoleMapper;
import com.advertisementboard.service.user.RoleService;
import com.advertisementboard.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserService userService;

    private final RoleService roleService;

    private final RoleMapper roleMapper;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

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
                        "/api/advertisements/{id}",
                        "/api/advertisements",
                        "/favicon.ico",
                        "/webjars/jquery/3.6.4/jquery.min.js"
                ).permitAll()
                .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/categories")
                .hasRole(UserRole.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.PUT, "/api/categories")
                .hasRole(UserRole.ADMINISTRATOR.name())
                .requestMatchers(HttpMethod.DELETE, "/api/categories")
                .hasRole(UserRole.ADMINISTRATOR.name())
                .requestMatchers("/api/advertisements/{id}/reject", "/api/advertisements/{id}/reject")
                .hasAnyRole(UserRole.MODERATOR.name(), UserRole.ADMINISTRATOR.name())
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
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

}
