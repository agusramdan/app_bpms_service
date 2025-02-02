package com.agus.ramdan.bmps.machine.controller;


import com.agus.ramdan.bmps.exception.BadRequestException;
import com.agus.ramdan.bmps.exception.ErrorValidation;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.machine.domain.TrxDepositMachine;
import com.agus.ramdan.bmps.machine.dto.TrxDepositMachineDto;
import com.agus.ramdan.bmps.machine.dto.TrxDepositMachineRequest;
import com.agus.ramdan.bmps.machine.dto.TrxDepositMachineResponse;
import com.agus.ramdan.bmps.machine.dto.TrxDepositMachineMapper;
import com.agus.ramdan.bmps.machine.repository.TrxDepositMachineRepository;

import com.agus.ramdan.bmps.service.QRCodeService;
import com.agus.ramdan.bmps.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping("/api/bpms/machine/deposit/transaction")
@RequiredArgsConstructor
@Validated
public class MachineDepositTrxController {

    private final TrxDepositMachineRepository repository;
    private final TrxDepositMachineMapper mapper;
    private final QRCodeService qrCodeService;

    @GetMapping("")
    @Operation(summary = "Get All")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TrxDepositMachine.class)))),
            @ApiResponse(responseCode = "204", description = "Data not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid tag value", content = @Content) })
    public ResponseEntity<List<TrxDepositMachineDto>> getAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "ids", required = false) String ids
    ) {
        val builder = new BaseSpecificationsBuilder<TrxDepositMachine>();
        if (StringUtils.hasText(ids)){
            val list = Arrays.stream(ids.split(","))
                    .map(String::trim) // Menghapus spasi di sekitar angka
                    .map(Long::parseLong) // Mengonversi ke Long
                    .collect(Collectors.toList());
            builder.ids_in(list);
            if (!list.isEmpty()){
                limit = Math.max(list.size(),limit);
            }
        }
        builder.withSearch(search);
        val spec = builder.build(BaseSpecifications::new);
        val pageable = new OffsetBasedPageRequest(offset,limit);
        val page = repository.findAll(spec,pageable);
        ChekUtils.ifEmptyThrow(page);
        val content = page.getContent().stream()
                .map(mapper::trxDepositMachineToTrxDepositMachineDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(content, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get By Id")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositMachineResponse.class)), })
    })
    public ResponseEntity<TrxDepositMachineResponse> getById(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        var data = ChekUtils.getOrThrow(repository.findById(id),()-> "Data not found for this id :: " + id);
        val response = mapper.trxDepositMachineToTrxDepositMachineResponse(data);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    @Operation(summary = "Deposit Transaction")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = TrxDepositMachineResponse.class)), })
    })
    public ResponseEntity<TrxDepositMachineResponse> postCreate(@RequestBody @Valid TrxDepositMachineRequest request) throws BadRequestException, ResourceNotFoundException {
        val errors = new ArrayList<ErrorValidation>();
        val qr_code = qrCodeService.validateCode(request.getQr_code());
        val option_trx = repository.findByQr_code(qr_code.getCode());
        if (option_trx.isPresent()){
            errors.add(new ErrorValidation("Deposit Transaction Duplicate QR Code","qr_code",qr_code.getCode()));
        }
        val new_data = mapper.trxDepositMachineRequestToTrxDepositMachine(request);
        //TODO Validate relation and constrain
        if(!errors.isEmpty()){
            throw new BadRequestException("Validation Error",errors.toArray(new ErrorValidation[0]));
        }
        val data = repository.save(new_data);
        qrCodeService.usedCode(qr_code.getCode());
        val response = mapper.trxDepositMachineToTrxDepositMachineResponse(data);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
