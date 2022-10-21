package com.forun.Forum.config.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthenticatedAuthorizationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration  {

    @Autowired
    private AutenticacaoService autenticacaoService;

    @Bean
    public SecurityFilterChain WebSecurityConfig(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                        //.antMatchers(HttpMethod.GET,"/post").permitAll()
                .antMatchers(HttpMethod.GET,"/post/*").permitAll()
                .antMatchers(HttpMethod.POST,"/user/register").permitAll()
                .antMatchers(HttpMethod.GET,"/h2").permitAll()
                .anyRequest().authenticated()
                        .and().formLogin(form -> form
                        .defaultSuccessUrl("/post",true)
                        .permitAll()
                        ).logout(logout -> logout.logoutUrl("/logout")).csrf().disable();
        ;
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.autenticacaoService).passwordEncoder(passwordEncoder());
    }

}
