package com.consafely.web.controller.auth;

import com.consafely.domain.jwt.Token;
import com.consafely.web.service.auth.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/token")
public class TokenController {
    private final TokenProvider tokenProvider;

    @GetMapping("/expired")
    public String auth() {
        throw new RuntimeException();
    }

    @GetMapping("/refresh")
    public String refreshAuth(HttpServletRequest request,
                              HttpServletResponse response) {
        String token = request.getHeader("RefreshToken");

        if(token != null && tokenProvider.verifyToken(token)) {
            String email = tokenProvider.getUid(token);
            Token newToken = tokenProvider.generateToken(email, "ROLE_USER");

            response.addHeader("Authorization", newToken.getAccessToken());
            response.addHeader("REFRESH_TOKEN", newToken.getRefreshToken());
            response.setContentType("application/json;charset=UTF-8");

            return "NEW ACCESS TOKEN";
        }

        throw new RuntimeException();
    }
}
