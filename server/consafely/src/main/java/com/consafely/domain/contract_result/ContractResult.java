package com.consafely.domain.contract_result;

import com.consafely.domain.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "CONTRACT_RESULTS")
public class ContractResult extends BaseTimeEntity {
    @Id
    @Column(name = "CONTRACT_RESULTS_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 2000)
    private String conditionResult;

    @Builder
    public ContractResult(String conditionResult){
        this.conditionResult = conditionResult;
    }

}
