package com.cos.photogramstart.global.config.security;

import com.cos.photogramstart.global.response.ResponseEnum;
import com.cos.photogramstart.handler.exception.TokenMissingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/auth")) {
            log.info("로그인/회원가입 요청");
            filterChain.doFilter(request, response);
            return;
        }

        String token = parseBearerToken(request);
        if (token == null) {
            throw new TokenMissingException("Token is missing");
        }
        log.info("JWT Auth 필터 실행");
        try {
            if (StringUtils.hasText(token)) {
                tokenProvider.validateToken(token);
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.warn("error");
            setErrorResponse(response, ResponseEnum.INVALID_JWT_SIGNATURE);
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, ResponseEnum.EXPIRED_JWT);
        }
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        log.info("bearerToken = {} ", bearerToken);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void setErrorResponse(HttpServletResponse response, ResponseEnum responseEnum) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(objectMapper.writeValueAsString(responseEnum.toString()));
    }
}
