package com.agus.ramdan.bmps.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@RequiredArgsConstructor
@Getter
public class Errors {
    private final String code;
    private final Date timestamp;
    private final String message;
    private final ErrorDetails[] details;
}