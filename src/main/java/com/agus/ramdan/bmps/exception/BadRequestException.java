package com.agus.ramdan.bmps.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
@Getter
public class BadRequestException extends Exception{
    private static final long serialVersionUID = 1L;
    private ErrorValidation[] errors;
    public BadRequestException(String message){
        super(message);
    }

    public BadRequestException(String message, ErrorValidation[] errors) {
        super(message);
        this.errors = errors;
    }
}
