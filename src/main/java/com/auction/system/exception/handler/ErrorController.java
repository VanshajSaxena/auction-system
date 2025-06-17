package com.auction.system.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auction.system.exception.DefaultRoleDoesNotExistException;
import com.auction.system.exception.EmailAlreadyExistsException;
import com.auction.system.exception.InternalJwtException;
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

  @ExceptionHandler(DefaultRoleDoesNotExistException.class)
  public ResponseEntity<ApiErrorDto> handleDefaultRoleDoesNotExistException(DefaultRoleDoesNotExistException ex) {
    log.warn(
        "The default role '%s' was not found in the database when the application context loaded"
            .formatted(ex.getRole()),
        ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .message("Something went wrong... Please try again later")
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InternalJwtException.class)
  public ResponseEntity<ApiErrorDto> handleInternalJwtException(InternalJwtException ex) {
    log.warn("""
        There is something wrong with the OAuth2ResourceServer configuration,
        this warning should not be happening.
                """, ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("Check your bearer JWT for malformation")
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
