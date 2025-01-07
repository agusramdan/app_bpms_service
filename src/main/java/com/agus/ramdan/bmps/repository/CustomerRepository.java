package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}