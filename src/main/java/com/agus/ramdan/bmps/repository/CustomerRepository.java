package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.Customer;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface CustomerRepository extends JpaRepositoryImplementation<Customer, Long> {
}