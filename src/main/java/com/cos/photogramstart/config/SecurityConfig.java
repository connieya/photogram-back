package com.cos.photogramstart.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
@Configuration // IOC
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.authorizeRequests()
               .antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**") // 다음과 같은 URL은..
               .authenticated() // 인증이 필요합니다.
               .anyRequest() // 그 외의 URL은..
               .permitAll() // 허용 해주겠습니다.
               .and()
               .formLogin() // 인증이 필요한 URL은
               .loginPage("/auth/signin") // 로그인 창으로 리다이렉션 해주고 URL은 다음과 같다.
               .defaultSuccessUrl("/"); // 로그인에 성공하면 다음 URL로 이동
    }
}
