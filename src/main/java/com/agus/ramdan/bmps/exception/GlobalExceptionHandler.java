package com.agus.ramdan.bmps.exception;

import java.util.Date;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Tracer;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler {
    private final Tracer tracer;
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "N/A";
        String spanId = tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : "N/A";
        val error = new Errors(new Date(), ex.getMessage(), traceId, spanId, request.getDescription(false), null);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Errors> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
        String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "N/A";
        String spanId = tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : "N/A";
        BindingResult result = ex.getBindingResult();
        val errors = result.getFieldErrors().stream()
                .map(violation -> new ErrorValidation(violation.getDefaultMessage(),violation.getField(),violation.getRejectedValue()) )
                .collect(Collectors.toList()).toArray(new ErrorValidation[0]);
        val error = new Errors(new Date(),"Validation Error",traceId,spanId, request.getDescription(false),errors);
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> badRequestException(BadRequestException ex, WebRequest request) {
        String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "N/A";
        String spanId = tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : "N/A";
        val error = new Errors(new Date(), ex.getMessage(),traceId,spanId,request.getDescription(false),ex.getErrors());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<?> noContentException(NoContentException ex, WebRequest request) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> globleExcpetionHandler(Exception ex, WebRequest request) {
        log.error(ex.getMessage(),ex);
        String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "N/A";
        String spanId = tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : "N/A";
        val error = new Errors(new Date(), "Internal Server Error Please Contact Helpdesk",traceId,spanId, request.getDescription(false),null);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}