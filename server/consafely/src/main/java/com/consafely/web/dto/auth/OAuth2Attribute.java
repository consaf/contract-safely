package com.consafely.web.dto.auth;

import com.consafely.domain.member.Role;
import com.consafely.domain.member.Member;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@ToString
@Getter
public class OAuth2Attribute {
    private Map<String, Object> attributes;
    private String attributeKey;
    private String name;
    private String email;

    @Builder
    public OAuth2Attribute(Map<String, Object> attributes,
                           String attributeKey,
                           String name,
                           String email) {
        this.attributes = attributes;
        this.attributeKey = attributeKey;
        this.name = name;
        this.email = email;
    }

    public static OAuth2Attribute of(String provider,
                                     String attributeKey,
                                     Map<String, Object> attributes) {

        switch(provider) {
            case "google":
                return ofGoogle(attributeKey, attributes);
            case "naver":
                return ofNaver("id", attributes);
            default:
                throw new RuntimeException();
        }

    }

    private static OAuth2Attribute ofGoogle(String attributeKey,
                                            Map<String, Object> attributes) {

        return OAuth2Attribute.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .attributes(attributes)
                .attributeKey(attributeKey)
                .build();
    }

    private static OAuth2Attribute ofNaver(String attributeKey,
                                           Map<String, Object> attributes){
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");

        return OAuth2Attribute.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .attributes(response)
                .attributeKey(attributeKey)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .role(Role.USER)
                .build();
    }
}
