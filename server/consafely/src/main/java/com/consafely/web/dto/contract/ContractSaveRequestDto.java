package com.consafely.web.dto.contract;

import com.consafely.domain.contract.Contract;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ContractSaveRequestDto {
    private String condition; // 계약 조항

    @Builder
    public ContractSaveRequestDto (String condition){
        this.condition = condition;
    }

    public Contract toEntity() {
        return Contract.builder()
                .condition(condition)
                .build();
    }
}
