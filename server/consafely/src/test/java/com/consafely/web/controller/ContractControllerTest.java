package com.consafely.web.controller;

import com.consafely.domain.contract.Contract;
import com.consafely.domain.contract.ContractRepository;
import com.consafely.web.dto.contract.ContractSaveRequestDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContractControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ContractRepository contractRepository;

    @AfterEach
    public void tearDown() throws Exception {
        contractRepository.deleteAll();
    }

    @Test
    public void 계약서_저장() throws Exception {

        //given
        LocalDateTime now = LocalDateTime.of(2023,1,2,0,0,0);
        String condition = "string";   // 계약 조항

        ContractSaveRequestDto requestDto = ContractSaveRequestDto.builder()
                .condition(condition)
                .build();

        String url = "http://localhost:"+port+"/contract";

        //when
        ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, requestDto, Long.class);

        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isGreaterThan(0L);

        List<Contract> all = contractRepository.findAll();
        assertThat(all.get(0).getCondition()).isEqualTo(condition);
    }

}
