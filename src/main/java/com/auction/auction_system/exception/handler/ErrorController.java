package com.auction.auction_system.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.auction.auction_system.exception.EmailAlreadyExistsException;
import com.auction.auction_system.exception.UsernameAlreadyExistsException;
import com.auction.auction_system.generated.models.ApiErrorDto;

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
}
