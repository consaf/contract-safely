package com.consafely.global.jwt;

import com.consafely.web.service.auth.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Arrays;

// 해당 필터는 토큰이 존재하는지? 토큰이 유효한 토큰인지? 토큰의 유효기간이 지나지 않았는지? 를 검증.
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends GenericFilterBean {
    private final TokenProvider tokenProvider;

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {


        //HttpServletRequest에서 헤더에 "X-AUTH-TOKEN"에 작성된 Token을 가져온다.
        String token = tokenProvider.resolveToken((HttpServletRequest) request);

        if (token != null && tokenProvider.verifyToken(token)) {
            // 토큰에서 secret Key를 사용하여 회원의 이메일을 가져온다.
            String uid = tokenProvider.getUid(token);

            //인증된 회원의 정보를 SecurityContextHolder에 저장
            //현재는 역할이 ROLE_USER 하나로 권한을 직접 주는 형태로 추후 바꿔야함
            Authentication auth = new UsernamePasswordAuthenticationToken(uid, "",
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        chain.doFilter(request, response);
    }
}
