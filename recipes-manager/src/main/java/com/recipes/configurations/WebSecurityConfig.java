package com.recipes.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String USER_ROLE = "USER";
    private static final String ADMIN_ROLE = "ADMIN";

    @Value("${credentials.user.username}")
    private String userUsername;
    @Value("${credentials.user.password}")
    private String userPassword;
    @Value("${credentials.admin.username}")
    private String adminUsername;
    @Value("${credentials.admin.password}")
    private String adminPassword;

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
                        .csrf().disable()
                        .cors()
                        .and()
                        .authorizeRequests()
                        .antMatchers(HttpMethod.POST, "/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.PUT, "/**").hasRole("ADMIN")
                        .antMatchers(HttpMethod.DELETE, "/**").hasRole("ADMIN")
                        .anyRequest().hasAnyRole(USER_ROLE, USER_ROLE)
                        .and()
                        .httpBasic()
                        .and()
                        .logout()
                        .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        final UserDetails user =
                        User.builder()
                                        .passwordEncoder((password) -> encoder().encode(password))
                                        .username(userUsername)
                                        .password(userPassword)
                                        .roles(USER_ROLE)
                                        .build();

        final UserDetails admin =
                        User.builder()
                                        .passwordEncoder((password) -> encoder().encode(password))
                                        .username(adminUsername)
                                        .password(adminPassword)
                                        .roles(ADMIN_ROLE)
                                        .build();

        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}