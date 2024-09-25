package com.example.coffeeshop.common;

import com.example.coffeeshop.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BusinessException ex) {

        log.error("Business exception: {}", ex.getMessage());

        return ResponseEntity.internalServerError()
                .body(
                        GeneralResponse.<String>builder()
                                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                                .data(ex.getMessage())
                                .build()
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> handleBadRequestException(BadRequestException ex) {

        log.error("Bad request exception : {}", ex.getMessage());

        return ResponseEntity.badRequest()
                .body(
                        GeneralResponse.<String>builder()
                                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                                .status(HttpStatus.BAD_REQUEST.name())
                                .data(ex.getMessage())
                                .build()
                );

    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        String contextPath = request.getContextPath();

        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream().map((fieldError) -> {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            return field + " " + errorMessage;
        }).toList();

        log.error("Invalid argument for path {} : {}", contextPath, errorMessages);

        return ResponseEntity.badRequest()
                .body(
                        GeneralResponse.<List<String>>builder()
                                .timestamp(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.UTC))
                                .status(HttpStatus.BAD_REQUEST.name())
                                .data(errorMessages)
                                .build()
                );
    }
}
