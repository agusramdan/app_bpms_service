package com.agus.ramdan.bpms.repository;

import com.agus.ramdan.bpms.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}