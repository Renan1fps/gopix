package com.gopix_jwt.demo.handles;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gopix_jwt.demo.errors.BadRequestException;
import com.gopix_jwt.demo.errors.BadRequestExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException bre){
        BadRequestExceptionDetails badRequestException = new BadRequestExceptionDetails();
        badRequestException.setMessage(bre.getMessage());
        badRequestException.setStatusCode(HttpStatus.BAD_REQUEST.value());
        badRequestException.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(badRequestException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerJWTtException(JWTVerificationException jwte){
        BadRequestExceptionDetails badRequestException = new BadRequestExceptionDetails();
        badRequestException.setMessage(jwte.getMessage());
        badRequestException.setStatusCode(HttpStatus.UNAUTHORIZED.value());
        badRequestException.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(badRequestException, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<BadRequestExceptionDetails> invalidCredentials(BadCredentialsException bce){
        BadRequestExceptionDetails badRequestException = new BadRequestExceptionDetails();
        badRequestException.setMessage(bce.getMessage());
        badRequestException.setStatusCode(HttpStatus.FORBIDDEN.value());
        badRequestException.setTimestamp(LocalDateTime.now());
        return new ResponseEntity<>(badRequestException, HttpStatus.FORBIDDEN);
    }
}
