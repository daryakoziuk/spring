package com.dmdev.service.contoller.handler;

import com.dmdev.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;

import static java.util.stream.Collectors.toList;

@RestControllerAdvice
public class RestControllerAdviceException {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ErrorResponse.builder()
                .message(exception.getBindingResult().getFieldErrors()
                        .stream().map(FieldError::getDefaultMessage).collect(toList()))
                .httpStatus(HttpStatus.BAD_REQUEST)
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse usernameNotFoundException(UsernameNotFoundException exception) {
        return ErrorResponse.builder()
                .message(Collections.singletonList(exception.getMessage()))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ErrorResponse accessDeniedException(AccessDeniedException accessDeniedException) {
        return ErrorResponse.builder()
                .message(Collections.singletonList(accessDeniedException.getMessage()))
                .httpStatus(HttpStatus.FORBIDDEN)
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse badCredentialsException(BadCredentialsException badCredentialsException) {
        return ErrorResponse.builder()
                .message(Collections.singletonList(badCredentialsException.getMessage()))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .dateTime(LocalDateTime.now())
                .build();
    }

    @ExceptionHandler(ServiceException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse customException(ServiceException exception) {
        return ErrorResponse.builder()
                .message(Collections.singletonList(exception.getMessage()))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .dateTime(LocalDateTime.now())
                .build();
    }
}
