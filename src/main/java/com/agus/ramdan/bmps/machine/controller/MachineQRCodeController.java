package com.agus.ramdan.bmps.machine.controller;

import com.agus.ramdan.bmps.domain.QRCode;
import com.agus.ramdan.bmps.dto.QRCodeResponse;
import com.agus.ramdan.bmps.exception.BadRequestException;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.service.QRCodeService;
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
@RequestMapping("/api/bpms/machine/qr_code")
@RequiredArgsConstructor
public class MachineQRCodeController {

    public final QRCodeService service;

    @GetMapping("validate/{code}")
    @Operation(summary = "Validate QR Code (api_validate_qr_code)")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = QRCode.class)),
                    })
    })

    public ResponseEntity<QRCodeResponse> validateCode(@PathVariable("code") String code) throws ResourceNotFoundException, BadRequestException {
        val response = service.validateCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping("use_code/{code}")
    @Operation(summary = "Validate QR Code (used code)")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = QRCode.class)),
                    })
    })

    public ResponseEntity<QRCodeResponse> usedCode(@PathVariable("code") String code) throws ResourceNotFoundException, BadRequestException {
        val response = service.usedCode(code);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
