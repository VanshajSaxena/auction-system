package com.auction.system.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auction.system.exception.DefaultRoleDoesNotExistException;
import com.auction.system.exception.EmailAlreadyExistsException;
import com.auction.system.exception.InternalJwtException;
import com.auction.system.exception.UserNotAuthenticatedException;
import com.auction.system.exception.UsernameAlreadyExistsException;
import com.auction.system.generated.models.ApiErrorDto;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ErrorController {

  @ExceptionHandler(UsernameAlreadyExistsException.class)
  public ResponseEntity<ApiErrorDto> handleUsernameAlreadyExistsException(UsernameAlreadyExistsException ex) {
    log.warn(ex.getMessage(), ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.CONFLICT.value())
        .message(
            "The username '%s' is already taken. Please select a different username.".formatted(ex.getUsername()))
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(EmailAlreadyExistsException.class)
  public ResponseEntity<ApiErrorDto> handleUsernameAlreadyExistsException(EmailAlreadyExistsException ex) {
    log.warn(ex.getMessage(), ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.CONFLICT.value())
        .message(
            "The email '%s' is already registered. Please login to continue.".formatted(ex.getEmail()))
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
  }

  @ExceptionHandler(UsernameNotFoundException.class)
  public ResponseEntity<ApiErrorDto> handleUsernameNotFoundException(UsernameNotFoundException ex) {
    log.warn(ex.getMessage(), ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .message(ex.getMessage())
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(DefaultRoleDoesNotExistException.class)
  public ResponseEntity<ApiErrorDto> handleDefaultRoleDoesNotExistException(DefaultRoleDoesNotExistException ex) {
    log.warn(ex.getMessage(), ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.NOT_FOUND.value())
        .message("Something went wrong... Please try again later")
        .build();
    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(InternalJwtException.class)
  public ResponseEntity<ApiErrorDto> handleInternalJwtException(InternalJwtException ex) {
    log.warn(ex.getMessage(), ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("Check your bearer JWT for malformation")
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(AuthorizationDeniedException.class)
  public ResponseEntity<ApiErrorDto> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
    log.info(ex.getMessage(), ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.FORBIDDEN.value())
        .message("You do not have the permission to access the resource.")
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
  }

  @ExceptionHandler(UserNotAuthenticatedException.class)
  public ResponseEntity<ApiErrorDto> handleAuthorizationDeniedException(UserNotAuthenticatedException ex) {
    log.warn(ex.getMessage(), ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.UNAUTHORIZED.value())
        .message("The user is not authenticated.")
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(IllegalStateException.class)
  public ResponseEntity<ApiErrorDto> handleAuthorizationDeniedException(IllegalStateException ex) {
    log.warn(ex.getMessage(), ex);
    ApiErrorDto errorResponse = ApiErrorDto.builder()
        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
        .message("Something went wrong.")
        .build();

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
