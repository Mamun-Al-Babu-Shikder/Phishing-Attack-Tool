package com.hack.demo.config;

import com.hack.demo.details.HackerDetails;
import com.hack.demo.entity.Hacker;
import com.hack.demo.service.HackerDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private HackerDetailsService hackerDetailsService;

    @SuppressWarnings("deprecation")
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        provider.setUserDetailsService(hackerDetailsService);
        auth.authenticationProvider(provider);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable()
               //.antMatcher("/**")
               .authorizeRequests()
               .antMatchers("/test","/register","/login", "/logout", "/assets/**", "/facebook/**").permitAll()
               //.anyRequest()
               .antMatchers("/dashboard", "/", "/attack-facebook", "/create-phishing-url",
                       "/fetch-hacking-data-table", "/delete-hacking-data", "/fetch_victim_data", "/fetch_victim_data_rest")
               .access("hasAnyRole('ROLE_HACKER') or hasAnyRole('ROLE_HACKER_ADMIN')")
               .and()
               .formLogin()
               .loginPage("/login").permitAll()
               .usernameParameter("email")
               .passwordParameter("password")
               .loginProcessingUrl("/login")
               .defaultSuccessUrl("/dashboard")
               .and()
               .logout()
               .logoutUrl("/logout")
               .logoutSuccessUrl("/login")
               .clearAuthentication(true)
               .invalidateHttpSession(true);

    }
}
