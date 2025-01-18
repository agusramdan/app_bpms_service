package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.ServiceLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface ServiceLocationRepository extends JpaRepositoryImplementation<ServiceLocation, Long> {
}