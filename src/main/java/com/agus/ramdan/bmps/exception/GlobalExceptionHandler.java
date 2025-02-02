package com.agus.ramdan.bmps.exception;

import java.util.Date;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.sleuth.Tracer;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    private final Tracer tracer;
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "N/A";
        String spanId = tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : "N/A";
        val error = new Errors(new Date(), ex.getMessage(),traceId,spanId,request.getDescription(false),null);
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
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
        logger.error(ex.getMessage(),ex);
        String traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "N/A";
        String spanId = tracer.currentSpan() != null ? tracer.currentSpan().context().spanId() : "N/A";
        val error = new Errors(new Date(), ex.getMessage(),traceId,spanId, request.getDescription(false),null);
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}