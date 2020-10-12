package com.funck.digitalbank.interfaces.handler;

import com.funck.digitalbank.domain.exceptions.BadRequestException;
import com.funck.digitalbank.domain.exceptions.NotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var message = ex.getMessage();
        var detail = ex.toString();
        return super.handleExceptionInternal(ex, Arrays.asList(new Error(message, detail)), headers, status, request);
    }

    @ExceptionHandler({BadRequestException.class})
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        var message = ex.getMessage();
        var detail = ex.toString();
        return super.handleExceptionInternal(ex, Arrays.asList(new Error(message, detail)), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({NotFoundException.class})
    public ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        var message = ex.getMessage();
        var detail = ex.toString();
        return super.handleExceptionInternal(ex, Arrays.asList(new Error(message, detail)), new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return super.handleExceptionInternal(ex, getErrorList(ex.getBindingResult()), headers, status, request);
    }

    private List<Error> getErrorList(BindingResult bindingResult) {
        return bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> new Error(fieldError.getDefaultMessage(), fieldError.toString()))
                .collect(Collectors.toList());
    }

    @RequiredArgsConstructor
    @Getter
    public static class Error {
        private final String error;
        private final String detail;
    }

}
