package com.jdh.auth.infra.exceprion;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static java.util.stream.Collectors.joining;

@RestControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CustomExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ExceptionResponse handler(BusinessException ex) {
        return new ExceptionResponse(ex.messages());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ExceptionResponse handler(DataIntegrityViolationException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handler(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        String mensagensFormatadas = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(joining(System.lineSeparator()));
        return new ExceptionResponse(mensagensFormatadas);
    }

    @ExceptionHandler(UnauthorizedUserException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handler(UnauthorizedUserException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionResponse handler(AccessDeniedException ex) {
        return new ExceptionResponse(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ExceptionResponse handler(Exception ex) {
        return new ExceptionResponse("Erro inesperado");
    }

}
