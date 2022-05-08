package com.medjamal.ouazani.springsecuritydemo.security;

import com.medjamal.ouazani.springsecuritydemo.helpers.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] PUBLIC_ROUTES = {
            "/api/auth/**"
    };

    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    AuthFilter authFilter(){
        return new AuthFilter();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and().csrf().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                .authorizeRequests()
                    .antMatchers(PUBLIC_ROUTES).permitAll()
                    .antMatchers("/api/user/admin").hasAnyRole(Constants.ROLE_ADMIN)
                    .antMatchers("/api/user/user").hasAnyRole(Constants.ROLE_USER, Constants.ROLE_ADMIN)
                    .antMatchers("/api/user/getAll").hasAnyRole(Constants.ROLE_ADMIN)
                    .antMatchers("/api/user/dev").hasAnyRole(Constants.ROLE_DEVELOPER, Constants.ROLE_ADMIN)
                    .anyRequest().authenticated()
                    .and()
                .addFilterBefore(authFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
