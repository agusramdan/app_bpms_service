package com.agus.ramdan.bmps.machine.repository;

import com.agus.ramdan.bmps.machine.domain.DepositMachineStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

public interface DepositMachineStatusRepository extends JpaRepositoryImplementation<DepositMachineStatus, Long> {
    @Transactional
    @Modifying
    @Query(value = "UPDATE deposit_machine_status SET status = 0 WHERE status = 2 and updated_on < :expiredTime", nativeQuery = true)
    int updateStatusUnAvailable(@Param("expiredTime") LocalDateTime expiredTime);
}