package com.consafely.web.dto.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MemberDto {

    private String email;
    private String name;

    @Builder
    public MemberDto(String email, String name) {
        this.email = email;
        this.name = name;
    }
}
