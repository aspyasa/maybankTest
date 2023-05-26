package com.maybank.testproject.exception;

import com.maybank.testproject.DTO.response.FailedDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<FailedDto> handleCustomException(GlobalException ex) {
        FailedDto errorResponse = new FailedDto(ex.getErrorCode(), ex.getErrorMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<FailedDto> handleHttp(HttpClientErrorException ex) {
        HttpStatus statusCode = (HttpStatus) ex.getStatusCode();
        String errorMessage;
        String errorCode;

        if (statusCode == HttpStatus.UNAUTHORIZED) {
            errorMessage = "Please make sure your GitHub token is valid.";
            errorCode = "UNAUTHORIZED";
        } else if (statusCode == HttpStatus.NOT_MODIFIED) {
            errorMessage = "Your GitHub token does not have the required permissions.";
            errorCode = "FORBIDDEN";
        } else if (statusCode == HttpStatus.UNPROCESSABLE_ENTITY) {
            errorMessage = "Validation failed, or the endpoint has been spammed.";
            errorCode = "UNPROCESSABLE_ENTITY";
        } else if (statusCode == HttpStatus.SERVICE_UNAVAILABLE) {
            errorMessage = "The service is currently unavailable. Please try again later.";
            errorCode = "SERVICE_UNAVAILABLE";
        } else {
            errorMessage = "An unexpected error occurred.";
            errorCode = "OTHER_EXCEPTION";
        }

        FailedDto failedDto = new FailedDto(errorMessage, errorCode);
        return ResponseEntity.status(statusCode).body(failedDto);
    }
}
