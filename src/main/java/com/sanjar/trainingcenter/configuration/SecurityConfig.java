package com.sanjar.trainingcenter.configuration;

import com.sanjar.trainingcenter.service.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HeaderWriterLogoutHandler;
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler;
import org.springframework.security.web.header.writers.ClearSiteDataHeaderWriter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl service;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf
                        .csrfTokenRequestHandler(new XorCsrfTokenRequestAttributeHandler())
                )
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/css/**", "/img/**", "/js/**").permitAll()
                        .requestMatchers("/", "/registration","/reset-password", "/application").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form.
                        loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .oauth2Login()
                .and()
                .oauth2Client(Customizer.withDefaults())
                .oauth2Login(form -> form.loginPage("/login"))
                .logout((logout) -> logout.logoutUrl("/logout")
                        .deleteCookies("XSRF-TOKEN")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login?logoutSuccess=true")
                        .addLogoutHandler(new HeaderWriterLogoutHandler(
                                new ClearSiteDataHeaderWriter(
                                        ClearSiteDataHeaderWriter.Directive.CACHE,
                                        ClearSiteDataHeaderWriter.Directive.COOKIES,
                                        ClearSiteDataHeaderWriter.Directive.STORAGE)))
                        .permitAll()
                )
                .userDetailsService(service)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
