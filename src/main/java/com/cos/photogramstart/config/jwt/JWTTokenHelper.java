package com.cos.photogramstart.config.jwt;

import com.cos.photogramstart.domain.user.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Component
public class JWTTokenHelper implements InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(JWTTokenHelper.class);
    private static final String AUTHORITIES_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 *30;
    private static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 *7;

    private final String secret;
    private final long tokenSeconds;
    private  Key key;

    public JWTTokenHelper(@Value("${jwt.secret") String secretKey, @Value("${jwt.token-validity-in-seconds}") long tokenSeconds) {
        this.secret = secretKey;
        this.tokenSeconds = tokenSeconds;
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(User user) {

        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenSeconds);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .signWith(key , SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    private Claims getAllClaimsFromToken(String token){
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJwt(token)
                    .getBody();
        }catch (Exception e){
            claims = null;
        }
        return claims;
    }

    public boolean validateToken(String token) {
        try{
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            logger.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            logger.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            logger.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            logger.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
