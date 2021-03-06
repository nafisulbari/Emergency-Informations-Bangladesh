package com.nafisulbari.eib.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


/**
 * SecurityConfiguration is responsible for configuring security,
 * authentication and providing access to users in different urls
 *
 * @author Ahmed Nafisul Bari
 */


@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {


    private CustomUserDetailsService customUserDetailsService;


    public SecurityConfiguration(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth
                .authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //assigning link access to user type
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/citizen/**").hasRole("CITIZEN")
                .antMatchers("/hospital/**").hasRole("HOSPITAL")
                .antMatchers("/police/**").hasRole("POLICE")
                .and()
                //login parameter username set to email.  login success will redirect to /home-detect in LoginController
                .formLogin().loginPage("/login").permitAll().usernameParameter("email").successForwardUrl("/home-detect")
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login")
                .and()
                .rememberMe().key("uniqueAndSecret").rememberMeParameter("remember-me").tokenValiditySeconds(86400);


    }

    // Database entries to authenticate login
    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(this.customUserDetailsService);
        return daoAuthenticationProvider;
    }

    //springs builtin Bcrypt password encoder enabled
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
