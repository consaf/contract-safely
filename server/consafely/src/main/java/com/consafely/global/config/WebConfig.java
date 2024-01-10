package com.consafely.global.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String LOCALHOST_REACTIVE = "http://localhost:3000";
    private static final String LOCALHOST_NATIVE = "http://localhost:19006";
    @Override
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**")  // CORS 적용할 URL 패턴 정의
                .allowedOrigins(LOCALHOST_REACTIVE)    // 자원 공유를 허락할 Origin 지정
                .allowedOrigins(LOCALHOST_NATIVE)
                .allowedMethods("*")    // 허용할 HTTP method 지정
                .allowedHeaders("Authorization", "Content-Type")    // 클아이언트 측의 CORS 요청에 허용되는 헤더 지정
                .exposedHeaders("*")    // 클라이언트측 응답에서 노출되는 헤더를 지정
                .allowCredentials(true) // 클라이언트 측에 대한 응답에 credentials(예: 쿠키, 인증헤더)를 포함할 수 있는 여부 지정
                .maxAge(3600);  // 원하는 시간만큼 pre-flight 리퀘스트 캐싱 가능
    }

}
