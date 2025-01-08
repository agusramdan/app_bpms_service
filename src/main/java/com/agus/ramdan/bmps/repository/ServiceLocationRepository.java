package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.ServiceLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceLocationRepository extends JpaRepository<ServiceLocation, Long> {
}