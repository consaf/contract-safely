package com.consafely.domain.member;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @Test
    public void 유저저장_불러오기() {

        //given
        String email = "test@test.com";
        String name = "test";
        Role role = Role.USER;


        memberRepository.save(Member.builder()
                .email(email)
                .name(name)
                .role(role)
                .build());

        //when
        List<Member> memberList = memberRepository.findAll();

        //then
        Member member = memberList.get(0);
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getRole()).isEqualTo(role);
    }
}
