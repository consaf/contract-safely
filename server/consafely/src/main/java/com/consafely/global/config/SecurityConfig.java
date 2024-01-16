package com.consafely.global.config;

import com.consafely.domain.auth.OAuth2SuccessHandler;
import com.consafely.global.jwt.JwtAuthFilter;
import com.consafely.web.service.auth.CustomOAuth2UserService;
import com.consafely.web.service.auth.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig {
    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final TokenProvider tokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용 X
            .csrf(AbstractHttpConfigurer::disable) // csrf 보안 사용 X
            .headers(httpSecurityHeadersConfigurer -> httpSecurityHeadersConfigurer
                    .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))  //보안 헤더 설정
            .sessionManagement((sessionManagement) -> sessionManagement
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 사용 X -> JWT 사용을 위함

            .authorizeHttpRequests(authorize -> authorize
                    .requestMatchers("/",
                            "/v3/api-docs/**",
                            "/swagger-ui/**",
                            "/token/**",
                            "/h2-console/**",
                            "/contract/**").permitAll() // 기본 접근 가능
//                    .requestMatchers("/api/v1/**").hasRole(Role.USER.name()) // Role User여야 가능
                    .anyRequest().authenticated()) // 나머지 경로는 모두 인증된 사용자만 접근 가능
//            .logout(logout -> logout.logoutSuccessUrl("/"))
            .addFilterBefore(new JwtAuthFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        http .oauth2Login(oauth2Login -> oauth2Login
//                .loginPage("/")
                .successHandler(oAuth2SuccessHandler)
                .userInfoEndpoint(UserInfoEndpointConfig -> UserInfoEndpointConfig
                        .userService(customOAuth2UserService))); // userService 설정

        return http.build();
    }
}
