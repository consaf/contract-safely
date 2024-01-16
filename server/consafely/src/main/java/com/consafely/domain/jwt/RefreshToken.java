package com.consafely.domain.jwt;

import com.consafely.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "REFRESH_TOKEN")
public class RefreshToken extends BaseTimeEntity {
    @Id
    @Column(name = "REFRESH_TOKEN_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "REFRESH_TOKEN")
    private Long refreshToken;

    @Column(name = "REFRESH_PERIOD")
    private Long refreshPeriod;
}
