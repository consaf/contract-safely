package com.consafely.web.controller.contract;

import com.consafely.web.dto.contract.ContractResponseDto;
import com.consafely.web.dto.contract.ContractSaveRequestDto;
import com.consafely.web.service.contract.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ContractController {
    private final ContractService contractService;

    @PostMapping("/contract")
    public Long save(@RequestBody ContractSaveRequestDto requestDto) {
        return contractService.save(requestDto);
    }

    @GetMapping("/contract/{id}")
    public ContractResponseDto findById (@PathVariable Long id) {
        return contractService.findById(id);
    }

    @DeleteMapping("/contract/{id}")
    public Long delete(@PathVariable Long id) {
        contractService.delete(id);
        return id;
    }
}
