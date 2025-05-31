package com.auction.system.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auction.system.exception.EmailAlreadyExistsException;
import com.auction.system.exception.UsernameAlreadyExistsException;
import com.auction.system.generated.models.ApiErrorDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorController {

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ApiErrorDto> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
    log.warn("The username already exists", ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.CONFLICT.value())
        .message(
            "The username '%s' is already taken. Please select a different username.".formatted(ex.getUsername()))
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ApiErrorDto> handleUsernameAlreadyExistsException(EmailAlreadyExistsException ex) {
    log.warn("The email already exists", ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.CONFLICT.value())
        .message(
            "The email '%s' is already registered. Please login to continue.".formatted(ex.getEmail()))
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(JwtException.class)
  public ResponseEntity<ApiErrorDto> handleJwtExceptionException(JwtException ex) {
    log.warn("Failed to retrive principal from the JWT", ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiErrorDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    log.warn("Failed resolution of username", ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }
}
