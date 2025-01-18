package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.VendorCrew;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface VendorCrewRepository extends JpaRepositoryImplementation<VendorCrew, Long> {
}