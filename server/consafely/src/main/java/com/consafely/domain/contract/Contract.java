package com.consafely.domain.contract;

import com.consafely.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "CONTRACTS")
public class Contract extends BaseTimeEntity {
    @Id
    @Column(name = "CONTRACT_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000, nullable = false)
    private String condition;  // 계약 조건

    @Builder
    public Contract (String condition) {
        this.condition = condition;
    }
}
