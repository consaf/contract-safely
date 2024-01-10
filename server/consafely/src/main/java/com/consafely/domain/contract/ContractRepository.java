package com.consafely.domain.contract;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContractRepository extends JpaRepository<Contract, Long> {

//  아래의 findAllDesc()는 모든 계약서 정보를 내림차순으로 정리 후 리스트화 시키는 것
    @Query("SELECT c FROM Contract c ORDER BY c.id DESC")
    List<Contract> findAllDesc();

}
