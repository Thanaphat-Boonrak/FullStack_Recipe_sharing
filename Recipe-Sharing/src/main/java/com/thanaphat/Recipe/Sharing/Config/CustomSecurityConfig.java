package com.thanaphat.Recipe.Sharing.Config;

import com.thanaphat.Recipe.Sharing.jwt.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class CustomSecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    SecurityFilterChain SecurityFilterChain(
            HttpSecurity http) throws  Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((session) -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS))
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry ->  authorizationManagerRequestMatcherRegistry.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                        )
                .addFilterBefore(jwtFilter,
                UsernamePasswordAuthenticationFilter.class)
        .formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }


    @Bean
    PasswordEncoder  passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }
}

