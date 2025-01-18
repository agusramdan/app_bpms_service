package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface PartnerRepository extends JpaRepositoryImplementation<Partner, Long> {
}