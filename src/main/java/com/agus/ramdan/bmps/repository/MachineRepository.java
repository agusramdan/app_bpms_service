package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.Machine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface MachineRepository extends JpaRepositoryImplementation<Machine, Long> {
}