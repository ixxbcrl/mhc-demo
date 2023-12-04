package com.example.mhcdemo.application.config;

import com.example.mhcdemo.application.query.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final AccountService accountService;

    private final PasswordEncoder passwordEncoder;

    public WebSecurityConfig(AccountService accountService, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PasswordEncoderConfig();
    }

    //This is where all the redirection, authentication, configuration and misery happens.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors(cors -> cors.disable()); //to enable scripts to load properly
        http.headers().frameOptions().disable(); //to disable iframe rendering for h2 console
        http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/sign-in", "/webjars/**", "/dist/**", "/plugins/**",
                                        "/images/**", "/css/**", "/js/**", "/WEB-INF/views/**").permitAll()
                                .requestMatchers("/eventDashboard", "/add-event", "/update-event/**"
                                        , "/h2-console/**").hasAnyAuthority("HR-ADMIN", "VENDOR-ADMIN")
                        //at this point I don't even know why I decided to use Thymeleaf anymore
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .permitAll()
                        .failureUrl("/login?error=true")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/eventDashboard", true)
                )
                .logout((logout) -> logout.permitAll());

        return http.build();
    }

    @Autowired
    public void configureGlobalAuth(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService)
                .passwordEncoder(passwordEncoder);
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider daoProvider = new DaoAuthenticationProvider();
        daoProvider.setUserDetailsService(accountService);
        daoProvider.setPasswordEncoder(passwordEncoder);
        return daoProvider;
    }
}
