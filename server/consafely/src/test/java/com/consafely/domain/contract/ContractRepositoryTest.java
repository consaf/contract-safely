package com.consafely.domain.contract;

import com.consafely.domain.contract.Contract;
import com.consafely.domain.contract.ContractRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ContractRepositoryTest {
    @Autowired
    ContractRepository contractRepository;

    @AfterEach
    public void cleanUp() {
        contractRepository.deleteAll();
    }

    @DisplayName("계약서를 저장한다.")
    @Test
    public void 계약서저장_불러오기_BaseTimeEntity_등록() {
        //given
        LocalDateTime now = LocalDateTime.of(2023,1,2,0,0,0);
        String condition = "string"; //계약 조항

        contractRepository.save(Contract.builder()
                .condition(condition)
                .build());
        //when
        List<Contract> contractList = contractRepository.findAll();

        //then
        Contract contract = contractList.get(0);
        assertThat(contract.getCondition()).isEqualTo(condition);
        assertThat(contract.getCreatedDate()).isAfter(now);
    }
}
