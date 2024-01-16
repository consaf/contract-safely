package com.consafely.web.service.auth;

import com.consafely.domain.jwt.Token;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class TokenProvider {
    private Key secretKey;

    @Value("${security.jwt.token.secret-key}")
    private String SECRET_KEY;

    @Value("${security.jwt.token.token-period}")
    private Long TOKEN_PERIOD; // 1시간

    @Value("${security.jwt.token.refresh-period}")
    private Long REFRESH_PERIOD; // 3주

    @PostConstruct
    protected void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(SECRET_KEY));
    }

    public Token generateToken(String uid, String role) {

        // 일반적으로 uid는 이메일로 명시되어 있다.
        Claims claims = Jwts.claims()
                .setIssuer("Server")
                .setAudience(uid);
        claims.put("role", role);

        // 임시 토큰 accessToken, refreshToken
        return new Token(
                Jwts.builder()
                        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                        .setSubject("Access Token")
                        .setClaims(claims)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + TOKEN_PERIOD))
                        .signWith(secretKey, SignatureAlgorithm.HS256)
                        .compact(),
                Jwts.builder()
                        .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                        .setSubject("Refresh Token")
                        .setClaims(claims)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + REFRESH_PERIOD))
                        .signWith(secretKey, SignatureAlgorithm.HS256)
                        .compact()
        );
    }

    public boolean verifyToken(String token) {
        try{
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey).build().parseClaimsJws(token);

            return claims.getBody().getExpiration()
                    .after(new Date());
        } catch (final UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT입니다.");
            return false;
        } catch (final MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
            return false;
        } catch (final SignatureException e) {
            log.info("토큰의 서명 유효성 검사가 실패했습니다.");
            return false;
        } catch (final ExpiredJwtException e) {
            log.info("토큰의 유효기간이 만료되었습니다.");
            return false;
        } catch (final Exception e) {
            log.info("알 수 없는 토큰 유효성 문제가 발생했습니다.");
            return false;
        }
    }

    public String getUid(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    /*
    * Header에서 X-ACCESS-TOKEN 으로 JWT 추출
     */
    public String resolveToken(
            HttpServletRequest request) {
        return request.getHeader("ACCESS_TOKEN");
    }
}
