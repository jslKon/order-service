package com.example.coffeeshop.common;

import com.example.coffeeshop.common.exception.BusinessException;
import com.example.coffeeshop.dto.GeneralResponse;
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

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleBadCredentialsException(BusinessException ex) {

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

        List<String> errorMessages = ex.getBindingResult().getFieldErrors().stream().map((fieldError) -> {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            return field + " " + errorMessage;
        }).toList();

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
