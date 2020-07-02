package com.wearewaes.hashcomparator.web;

import com.wearewaes.hashcomparator.exceptions.HashPositionAlreadyExistException;
import com.wearewaes.hashcomparator.exceptions.InvalidBase64Exception;
import com.wearewaes.hashcomparator.exceptions.MissingHashException;
import com.wearewaes.hashcomparator.web.dto.ApiError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler({ HttpMessageNotReadableException.class })
    public ResponseEntity<Object> handleNotReadableException(
            HttpMessageNotReadableException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ HashPositionAlreadyExistException.class })
    public ResponseEntity<Object> HandleHashPositionAlreadyExistException(
            RuntimeException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ MissingHashException.class })
    public ResponseEntity<Object> HandleMissingHashException(
            RuntimeException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }

    @ExceptionHandler({ InvalidBase64Exception.class })
    public ResponseEntity<Object> HandleInvalidBase64Exception(
            RuntimeException ex, WebRequest request) {
        return this.getError(ex, request);
    }

    private ResponseEntity<Object> getError(RuntimeException ex, WebRequest request) {
        String error = ex.getMessage();
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
        return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
    }
}
