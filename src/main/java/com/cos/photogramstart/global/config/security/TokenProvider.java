package com.cos.photogramstart.global.config.security;

import com.cos.photogramstart.global.config.security.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.domain.User;
import com.cos.photogramstart.domain.user.infrastructure.UserRepository;
import com.cos.photogramstart.global.error.exception.EntityNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

import static com.cos.photogramstart.global.error.ErrorCode.*;

@Service
@Slf4j
public class TokenProvider {

    @Autowired
    private UserRepository userRepository;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 *30000;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 *7;

    private Key key;
    
    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }
    public TokenDto generateTokenDto(Authentication authentication) {
        long now = (new Date()).getTime();
        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
        Date accessTokenExpiresIn = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        User userEntity = principalDetails.getUser();

        ClaimDto claimDto = ClaimDto.builder().id(userEntity.getId())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail()).build();

        String accessToken = Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, claimDto)
                .setExpiration(accessTokenExpiresIn)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto
                .builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .build();
    }

    public Authentication getAuthentication(String accessToken){
        Claims claims = parseClaims(accessToken);
        if(claims.get(AUTHORITIES_KEY) == null){
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        ClaimDto claimDto = mapper.convertValue(claims.get(AUTHORITIES_KEY), ClaimDto.class);
        User user = userRepository.findById(claimDto.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException(USER_NOT_FOUND);
        });
        PrincipalDetails principalDetails = new PrincipalDetails(user);
        return new UsernamePasswordAuthenticationToken(principalDetails,"",principalDetails.getAuthorities());
    }

    public void validateToken(String token) {
        try {
            log.info("token = {}" ,token);
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

        }catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("잘못된 JWT 서명입니다. ={}",e);
            throw e;
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
            throw e;
        }

    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        }catch (ExpiredJwtException e){
            return e.getClaims();
        }
    }
}
