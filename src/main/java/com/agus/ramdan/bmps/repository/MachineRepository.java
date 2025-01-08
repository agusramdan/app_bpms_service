package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {
}