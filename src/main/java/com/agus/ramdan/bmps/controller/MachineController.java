package com.agus.ramdan.bmps.controller;

import com.agus.ramdan.bmps.domain.Machine;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.repository.MachineRepository;
import com.agus.ramdan.bmps.utils.BeanUtils;
import com.agus.ramdan.bmps.utils.ChekUtils;
import com.agus.ramdan.bmps.utils.OffsetBasedPageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bmps/machine")
public class MachineController {
    @Autowired
    MachineRepository repository;

    @GetMapping("")
    @Operation(summary = "Get All")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Machine.class)))),
            @ApiResponse(responseCode = "204", description = "Data not found", content = @Content),
            @ApiResponse(responseCode = "400", description = "Invalid tag value", content = @Content) })
    public ResponseEntity<List<Machine>> getAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit
    ) {
        var pageable = new OffsetBasedPageRequest(offset,limit);
        var page = repository.findAll(pageable);
        ChekUtils.ifEmptyThrow(page);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get By Id")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Machine.class)), })
    })
    public ResponseEntity<Machine> getById(@PathVariable("id") long id)
            throws ResourceNotFoundException {
        var data = ChekUtils.getOrThrow(repository.findById(id),()-> "Data not found for this id :: " + id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping
    @Operation(summary = "Create")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Machine.class)), })
    })
    public ResponseEntity<Machine> postCreate(@RequestBody Machine new_data) {
        var data = repository.save(new_data);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Data")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Machine.class)), })
    })
    public ResponseEntity<Machine> putUpdate(
            @PathVariable("id") long id,
            @RequestBody Machine update_data)
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
