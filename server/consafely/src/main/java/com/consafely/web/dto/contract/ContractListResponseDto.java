package com.consafely.web.dto.contract;

import com.consafely.domain.contract.Contract;
import lombok.Getter;

@Getter
public class ContractListResponseDto {
    private Long id;
    private String condition;

    public ContractListResponseDto(Contract entity) {
        this.id = entity.getId();
        this.condition = entity.getCondition();
    }
}
