package com.agus.ramdan.bmps.machine.repository;

import com.agus.ramdan.bmps.machine.domain.TrxDepositMachine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TrxDepositMachineRepository extends JpaRepositoryImplementation<TrxDepositMachine, Long> {
    @Query("SELECT e FROM TrxDepositMachine e WHERE e.qr_code = :qr_code")
    Optional<TrxDepositMachine> findByQr_code(@Param("qr_code") String qr_code);
}