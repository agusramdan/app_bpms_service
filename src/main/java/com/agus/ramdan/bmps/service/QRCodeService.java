package com.agus.ramdan.bmps.service;

import com.agus.ramdan.bmps.domain.QRCode;
import com.agus.ramdan.bmps.dto.QRCodeRequest;
import com.agus.ramdan.bmps.dto.QRCodeResponse;
import com.agus.ramdan.bmps.exception.BadRequestException;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.repository.QRCodeRepository;
import com.agus.ramdan.bmps.utils.BeanUtils;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class QRCodeService {
    private final RandomStringGenerator generator;
    private final QRCodeRepository repository;

    public QRCode createCode(QRCode request) {
        var generate_code = !StringUtils.hasText(request.getCode());
        while (generate_code) {
            request.setCode(generator.generateRandomString());
            generate_code = repository.findByCode(request.getCode()).isPresent();
        }
        return repository.save(request);
    }

    public QRCodeResponse createCode(QRCodeRequest request) {
        var qrCode = new QRCode();
        BeanUtils.copyNonNullProperties(request, qrCode);
        qrCode = this.createCode(qrCode);
        val response = new QRCodeResponse();
        BeanUtils.copyNonNullProperties(qrCode,response);
        return response;
    }
    public QRCodeResponse validateCode(String code) throws ResourceNotFoundException, BadRequestException {
        val qrCode = repository.findByCode(code).orElseThrow(()->new ResourceNotFoundException("QR Code Not Found"));
        if (Boolean.TRUE.equals(qrCode.getIs_used())){
            throw new BadRequestException("QR Code Already used");
        }
        val response = new QRCodeResponse();
        BeanUtils.copyNonNullProperties(qrCode,response);
        return response;
    }

    public QRCodeResponse usedCode(String code) throws ResourceNotFoundException, BadRequestException {
        val qrCode = repository.findByCode(code).orElseThrow(()->new ResourceNotFoundException("QR Code Not Found"));
        if (Boolean.TRUE.equals(qrCode.getIs_used())){
            throw new BadRequestException("QR Code Already used");
        }
        qrCode.setIs_used(Boolean.TRUE);
        repository.save(qrCode);
        val response = new QRCodeResponse();
        BeanUtils.copyNonNullProperties(qrCode,response);
        return response;
    }
}
