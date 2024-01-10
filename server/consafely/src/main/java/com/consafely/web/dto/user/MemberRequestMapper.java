package com.consafely.web.dto.user;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

@Component
public class MemberRequestMapper {
    public static MemberDto toDto(OAuth2User oAuth2User) {
        var attributes = oAuth2User.getAttributes();
        return MemberDto.builder()
                .email((String) attributes.get("email"))
                .name((String) attributes.get("name"))
                .build();
    }
}
