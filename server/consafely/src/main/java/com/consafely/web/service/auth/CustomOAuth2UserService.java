package com.consafely.web.service.auth;

import com.consafely.web.dto.auth.OAuth2Attribute;
import com.consafely.domain.member.Member;
import com.consafely.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // DefaultOAuth2UserService 객체를 성공 정보를 바탕으로 만든다.
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

        // 생성된 Service 객체로 부터 User를 받는다.
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 로그인 진행중인 서비스를 구분하는 ID -> 여러 개 소셜 로그인이 필요할 때 사용하는 ID
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // OAuth2 로그인 진행 시 키가 되는 필드 값(Primary key) -> 구글은 기본 값("sub") 지원
        // 그러나 네이버, 카카오 로그인 시 필요한 값
        String userNameAtrributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        log.info("registrationId = {}", registrationId);
        log.info("userNameAttributeName = {}", userNameAtrributeName);

        // OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담은 클래스
        // SuccessHandler가 사용할 수 있도록 등록해준다.
        OAuth2Attribute attributes =
                OAuth2Attribute.of(registrationId, userNameAtrributeName, oAuth2User.getAttributes());

        log.info("oAuth2User.getAttributes() = {}", oAuth2User.getAttributes());

        // 회원정보 DB 등록
        Member member = saveOrUpdate(attributes);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(member.getRoleKey())),
                attributes.getAttributes(), attributes.getAttributeKey());
    }

    private Member saveOrUpdate(OAuth2Attribute attributes) {
        //소셜 로그인의 회원 정보가 업데이트 되었다면, 기존 DB에 저장된 회원의 이름을 업데이트
        Member member = memberRepository.findByEmail((String) attributes.getEmail())
                .map(entity -> entity.update(attributes.getName())) // 회원 이름 업데이트
                .orElse(attributes.toEntity()); // DB에 존재하지 않을 시 Entity 변환

        // DB에 등록된 이메일 아니라면, DB에 등록(회원가입)을 진행시킨다.
        return memberRepository.save(member);
    }
}
