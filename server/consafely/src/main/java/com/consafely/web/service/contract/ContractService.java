package com.consafely.web.service.contract;

import com.consafely.domain.contract.Contract;
import com.consafely.domain.contract.ContractRepository;
import com.consafely.web.dto.contract.ContractListResponseDto;
import com.consafely.web.dto.contract.ContractResponseDto;
import com.consafely.web.dto.contract.ContractSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ContractService {
    private final ContractRepository contractRepository;

    @Transactional
    public Long save(ContractSaveRequestDto requestDto) {
        return contractRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public void delete(Long id) {
        Contract contract = contractRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 계약서가 존재하지 않습니다. id = "+ id));

        contractRepository.delete(contract);
    }

    @Transactional(readOnly = true)
    public List<ContractListResponseDto> findAllDesc() {
        return contractRepository.findAllDesc().stream()
                .map(ContractListResponseDto::new)
                .collect(Collectors.toList());
    }

    public ContractResponseDto findById(Long id) {
        Contract entity = contractRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 계약서가 존재하지 않습니다. id = "+ id));

        return new ContractResponseDto(entity);
    }
}
