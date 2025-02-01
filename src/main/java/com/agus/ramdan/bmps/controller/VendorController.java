package com.agus.ramdan.bmps.controller;

import com.agus.ramdan.bmps.domain.Customer;
import com.agus.ramdan.bmps.domain.CustomerCrew;
import com.agus.ramdan.bmps.domain.Vendor;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.repository.VendorRepository;
import com.agus.ramdan.bmps.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bmps/vendor")
public class VendorController {
    @Autowired
    VendorRepository repository;

    @GetMapping("")
    @Operation(summary = "Get All")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Vendor.class)))),
            @ApiResponse(responseCode = "204", description = "Data not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid tag value", content = @Content) })
    public ResponseEntity<List<Vendor>> getAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "ids", required = false) String ids
    ) {
        val builder = new BaseSpecificationsBuilder<Vendor>();
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
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get By Id")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Vendor.class)), })
    })
    public ResponseEntity<Vendor> getById(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        var data = ChekUtils.getOrThrow(repository.findById(id),()-> "Data not found for this id :: " + id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping
    @Operation(summary = "Create")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Vendor.class)), })
    })
    public ResponseEntity<Vendor> postCreate(@RequestBody Vendor new_data) {
        var data = repository.save(new_data);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Data")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Vendor.class)), })
    })
    public ResponseEntity<Vendor> putUpdate(
            @PathVariable("id") long id,
            @RequestBody Vendor update_data)
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
