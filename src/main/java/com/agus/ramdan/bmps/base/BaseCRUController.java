package com.agus.ramdan.bmps.base;

import com.agus.ramdan.bmps.exception.Errors;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.utils.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.val;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public interface BaseCRUController<T,ID> extends BaseController<T,ID> {



    @GetMapping("")
    @Operation(summary = "Get All")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema()))),
            @ApiResponse(responseCode = "204", description = "No Content",
                    content = @Content(schema = @Schema(implementation = Errors.class))),
            @ApiResponse(responseCode = "400", description = "Invalid tag value",
                    content = @Content(schema = @Schema(implementation = Errors.class))) })
    default ResponseEntity<List<T>> getAll(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "25") int limit,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "ids", required = false) String ids
    ) {
        val builder = new BaseSpecificationsBuilder<T>();
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
        val page = getRepository().findAll(spec,pageable);
        ChekUtils.ifEmptyThrow(page);
        return new ResponseEntity<>(page.getContent(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Get By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Errors.class)))

    })
    default ResponseEntity<T> getById(@PathVariable("id") ID id)
            throws ResourceNotFoundException {
        var data = ChekUtils.getOrThrow(getRepository().findById(id),()-> "Data not found for this id :: " + id);
        return ResponseEntity.ok().body(data);
    }

    @PostMapping
    @Operation(summary = "Create")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description = "successful operation",content = {
                    @Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Errors.class))})
    })
    default ResponseEntity<T> postCreate(@RequestBody T new_data) {
        var data = getRepository().save(new_data);
        return ResponseEntity.status(HttpStatus.CREATED).body(data);
    }
    @PutMapping("/{id}")
    @Operation(summary = "Update Data")
    @ApiResponses(value = {
            @ApiResponse(description = "successful operation",content = {
                    @Content(mediaType = "application/json") }),
            @ApiResponse(responseCode = "400",description = "Bad Request",content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Errors.class))})
    })
    default ResponseEntity<T> putUpdate(
            @PathVariable("id") ID id,
            @RequestBody T update_data)
            throws ResourceNotFoundException {
        var data = ChekUtils.getOrThrow(getRepository().findById(id),()-> "Data not found for this id :: " + id);
        BeanUtils.copyNonNullProperties(update_data, data);
        var result_data = getRepository().save(data);
        return ResponseEntity.ok().body(result_data);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "successful operation"),
            @ApiResponse(responseCode = "404", description = "Not Found",
                    content = @Content(mediaType = "application/json",schema = @Schema(implementation = Errors.class)))
    })
    @Operation(summary = "Delete Data By Id")
    default ResponseEntity<HttpStatus> deleteData(@PathVariable("id") ID id) throws ResourceNotFoundException{
        var data = ChekUtils.getOrThrow(getRepository().findById(id),()-> "Data not found for this id :: " + id);
        getRepository().deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
