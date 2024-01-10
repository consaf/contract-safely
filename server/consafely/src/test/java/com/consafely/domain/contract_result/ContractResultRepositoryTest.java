package com.consafely.domain.contract_result;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ContractResultRepositoryTest {
    @Autowired
    ContractResultRepository contractResultRepository;

    @AfterEach
    public void cleanup() {
        contractResultRepository.deleteAll();
    }

    @Test
    public void 계약서결과저장_불러오기() {

        //given
        String conditionResult = "계약 조항입니다.";

        contractResultRepository.save(ContractResult.builder()
                .conditionResult(conditionResult)
                .build());

        //when
        List<ContractResult> contractResultList = contractResultRepository.findAll();

        //then
        ContractResult contractResult = contractResultList.get(0);
        assertThat(contractResult.getConditionResult()).isEqualTo(conditionResult);
    }
}
