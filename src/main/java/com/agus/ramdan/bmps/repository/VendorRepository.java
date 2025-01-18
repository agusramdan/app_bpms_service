package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface VendorRepository extends JpaRepositoryImplementation<Vendor, Long> {
}