package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.ServiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ServiceProductRepository extends JpaRepositoryImplementation<ServiceProduct, Long> {
}