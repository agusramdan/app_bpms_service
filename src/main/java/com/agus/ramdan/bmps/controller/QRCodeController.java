package com.agus.ramdan.bmps.controller;

import com.agus.ramdan.bmps.domain.QRCode;
import com.agus.ramdan.bmps.dto.QRCodeRequest;
import com.agus.ramdan.bmps.dto.QRCodeResponse;
import com.agus.ramdan.bmps.exception.BadRequestException;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.repository.QRCodeRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
@RestController
@RequestMapping("/api/bpms/qr_code")
@RequiredArgsConstructor
public class QRCodeController {

    private final QRCodeService service;
    private final QRCodeRepository repository;

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


    @GetMapping("")
    @Operation(summary = "Get All")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = QRCode.class)))),
            @ApiResponse(responseCode = "204", description = "Data not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid tag value", content = @Content) })
    public ResponseEntity<List<QRCode>> getAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "ids", required = false) String ids
    ) {
        val builder = new BaseSpecificationsBuilder<QRCode>();
        if (StringUtils.hasText(ids)){
            val list =Arrays.stream(ids.split(","))
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
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get By Id")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QRCode.class)), })
    })
    public ResponseEntity<QRCode> getById(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        var data = ChekUtils.getOrThrow(repository.findById(id),()-> "Data not found for this id :: " + id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping
    @Operation(summary = "Create")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QRCode.class)), })
    })
    public ResponseEntity<QRCode> postCreate(@RequestBody QRCode new_data) {
        var data = repository.save(new_data);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Data")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = QRCode.class)), })
    })

    public ResponseEntity<QRCode> putUpdate(
            @PathVariable("id") long id,
            @RequestBody QRCode update_data)
            throws ResourceNotFoundException {
        var data = ChekUtils.getOrThrow(repository.findById(id),()-> "Data not found for this id :: " + id);
        BeanUtils.copyNonNullProperties(update_data, data);
        var result_data = repository.save(data);
        return ResponseEntity.ok().body(result_data);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "successful operation")})
    @Operation(summary = "Delete Data By Id")
    public ResponseEntity<HttpStatus> deleteData(@PathVariable("id") long id) throws ResourceNotFoundException{
        var data = ChekUtils.getOrThrow(repository.findById(id),()-> "Data not found for this id :: " + id);
        repository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
