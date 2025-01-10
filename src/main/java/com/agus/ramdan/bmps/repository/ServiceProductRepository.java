package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.ServiceProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceProductRepository extends JpaRepository<ServiceProduct, Long> {
}