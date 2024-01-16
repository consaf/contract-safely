package com.consafely.web.dto.contract;

import com.consafely.domain.contract.Contract;
import lombok.Getter;

@Getter
public class ContractResponseDto {

    private Long id;
    private String condition;  // 계약 조항

    public ContractResponseDto(Contract entity) {
        this.id = id;
        this.condition = entity.getCondition();
    }
}
