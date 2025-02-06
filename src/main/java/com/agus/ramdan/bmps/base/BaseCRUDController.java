package com.agus.ramdan.bmps.base;

import com.agus.ramdan.bmps.exception.Errors;
import com.agus.ramdan.bmps.exception.ResourceNotFoundException;
import com.agus.ramdan.bmps.utils.ChekUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface BaseCRUDController<T,ID> extends BaseCRUController<T,ID> {

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
