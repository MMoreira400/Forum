package com.forun.Forum.config.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration  {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers(HttpMethod.GET,"/topicos").permitAll()
               .antMatchers(HttpMethod.GET,"/topicos/*").permitAll()
               .anyRequest().authenticated()
               .and().formLogin();
        return http.build();
    }



}
