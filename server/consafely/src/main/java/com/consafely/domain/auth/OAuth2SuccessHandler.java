package com.consafely.domain.auth;

import com.consafely.domain.jwt.Token;
import com.consafely.web.dto.user.MemberDto;
import com.consafely.web.dto.user.MemberRequestMapper;
import com.consafely.web.service.auth.TokenProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final TokenProvider tokenProvider;
    private final MemberRequestMapper memberRequestMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        log.info("OAuth2 소셜 로그인이 성공적으로 진행되어 토큰 발행을 시작하겠습니다.");

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        MemberDto memberDto = memberRequestMapper.toDto(oAuth2User);

        Token token = tokenProvider.generateToken(memberDto.getEmail(), "USER");
        log.info("토큰 생성 완료 : {}", token);

        // 토큰을 파싱해서 넘겨준다.
        String targetUrl = UriComponentsBuilder.fromUriString("/")
                .queryParam("token", token)
                .build().toUriString();

        log.info("토큰을 파싱하여 다음 Url로 넘겨주겠습니다. {}", targetUrl);

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


//    토큰 넘어오는지 확인하기 위한 메소드.
//    private void writeTokenResponse(HttpServletResponse response, Token token) throws IOException {
//        response.setContentType("text/html;charset=UTF-8");
//
//        response.addHeader("ACCESS_TOKEN", token.getAccessToken());
//        response.addHeader("REFRESH_TOKEN", token.getRefreshToken());
//        response.setContentType("application/json;charset=UTF-8");
//
//        var writer = response.getWriter();
//        writer.println(objectMapper.writeValueAsString(token));
//        writer.flush();
//    }
}
