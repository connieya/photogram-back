package com.cos.photogramstart.config;

import com.cos.photogramstart.config.jwt.JWTAuthenticationFilter;
import com.cos.photogramstart.config.jwt.JWTTokenHelper;
import com.cos.photogramstart.config.jwt.JwtAccessDeniedHandler;
import com.cos.photogramstart.config.jwt.JwtAuthenticationEntryPoint;
import com.cos.photogramstart.oauth.OAuth2DetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
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
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CorsConfig corsConfig;
    private final OAuth2DetailService oAuth2DetailService;

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder encode(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                formLogin().disable()
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and()
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JWTAuthenticationFilter(tokenHelper) , UsernamePasswordAuthenticationFilter.class)

        .authorizeRequests()
                .antMatchers("/api/user/**","/user/**","/image/**","/subscribe/**","/comment/**,/api/**")
                .authenticated()
                .anyRequest().permitAll();
    }
}
