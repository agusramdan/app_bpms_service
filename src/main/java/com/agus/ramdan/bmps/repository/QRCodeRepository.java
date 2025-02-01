package com.agus.ramdan.bmps.repository;

import com.agus.ramdan.bmps.domain.QRCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.Optional;

public interface QRCodeRepository extends JpaRepositoryImplementation<QRCode, Long> {
    Optional<QRCode> findByCode(String code);
}