package com.cos.photogramstart.config;

import com.cos.photogramstart.config.jwt.JWTAuthenticationFilter;
import com.cos.photogramstart.config.jwt.JWTTokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration // IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTTokenHelper tokenHelper;
    private final CorsConfig corsConfig;

    @Bean
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JWTAuthenticationFilter(tokenHelper) , UsernamePasswordAuthenticationFilter.class)
                .formLogin().disable()
                .httpBasic().disable()
        .authorizeRequests()
                .antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**,/api/**")
                .authenticated()
                .anyRequest()
                .permitAll();
    }
}
