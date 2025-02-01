package com.agus.ramdan.bmps.mobile.controller;

import com.agus.ramdan.bmps.domain.QRCode;
import com.agus.ramdan.bmps.dto.QRCodeResponse;
import com.agus.ramdan.bmps.dto.QRCodeRequest;
import com.agus.ramdan.bmps.service.QRCodeService;
import com.agus.ramdan.bmps.utils.BeanUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 *
 */
@RestController
@RequestMapping("/api/bpms/mobile/qr_code")
@RequiredArgsConstructor
public class MobileQRCodeController {
    private final QRCodeService service;

    @PostMapping("generate")
    @Operation(summary = "Generate QR Code")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",
                    content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QRCode.class)),
                             })
    })
    public ResponseEntity<QRCodeResponse> createCode(@RequestBody QRCodeRequest request) {
        var response =service.createCode(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
