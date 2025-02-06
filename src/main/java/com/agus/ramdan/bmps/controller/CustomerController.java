package com.agus.ramdan.bmps.controller;

import com.agus.ramdan.bmps.base.BaseCRUDController;
import com.agus.ramdan.bmps.domain.Customer;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.repository.CustomerRepository;
import com.agus.ramdan.bmps.utils.ChekUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping({"/api/bmps/customer","/api/bmps/web/customer","/api/bmps/crud/customer",})
@RequiredArgsConstructor
public class CustomerController implements BaseCRUDController<Customer,Long> {

    @Getter
    private final CustomerRepository repository;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",array = @ArraySchema(schema = @Schema(implementation = Customer.class)))),
            })
    @Override
    public ResponseEntity<List<Customer>> getAll(int offset, int limit, String search, String ids) {
        return BaseCRUDController.super.getAll(offset, limit, search, ids);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)), })
    })
    @Override
    public ResponseEntity<Customer> getById(Long aLong) throws ResourceNotFoundException {
        return BaseCRUDController.super.getById(aLong);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)), })
    })
    @Override
    public ResponseEntity<Customer> postCreate(Customer new_data) {
        return BaseCRUDController.super.postCreate(new_data);
    }

    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Customer.class)), })
    })
    @Override
    public ResponseEntity<Customer> putUpdate(Long aLong, Customer update_data) throws ResourceNotFoundException {
        return BaseCRUDController.super.putUpdate(aLong, update_data);
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
