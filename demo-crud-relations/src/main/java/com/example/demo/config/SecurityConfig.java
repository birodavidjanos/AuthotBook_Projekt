package com.example.demo.config;

import com.example.demo.service.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JpaUserDetailsService userDetailsService;

    public SecurityConfig(JpaUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))
                .authorizeHttpRequests(auth -> auth
                        // publikus erőforrások
                        .requestMatchers(
                                new AntPathRequestMatcher("/"),
                                new AntPathRequestMatcher("/css/**"),
                                new AntPathRequestMatcher("/js/**"),
                                new AntPathRequestMatcher("/images/**"),
                                new AntPathRequestMatcher("/h2-console/**")
                        ).permitAll()

                        // ADMIN-only műveletek (szerkesztés/törlés) - POST/DELETE metodokkal
                        .requestMatchers(new AntPathRequestMatcher("/books/*/delete", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/books/*/edit", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/books/*", "DELETE")).hasRole("ADMIN")

                        .requestMatchers(new AntPathRequestMatcher("/authors/*/delete", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/authors/*/edit", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/authors/*", "DELETE")).hasRole("ADMIN")

                        .requestMatchers(new AntPathRequestMatcher("/students/*/delete", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/students/*/edit", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/students/*", "DELETE")).hasRole("ADMIN")

                        .requestMatchers(new AntPathRequestMatcher("/courses/*/delete", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/courses/*/edit", "POST")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/courses/*", "DELETE")).hasRole("ADMIN")

                        // általános hozzáférés bejelentkezetteknek (olvasás, listák stb.)
                        .requestMatchers(
                                new AntPathRequestMatcher("/books/**"),
                                new AntPathRequestMatcher("/authors/**"),
                                new AntPathRequestMatcher("/students/**"),
                                new AntPathRequestMatcher("/courses/**")
                        ).authenticated()

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        http.authenticationProvider(authenticationProvider());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userDetailsService;
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider p = new DaoAuthenticationProvider();
        p.setUserDetailsService(userDetailsService);
        p.setPasswordEncoder(passwordEncoder());
        return p;
    }
}
