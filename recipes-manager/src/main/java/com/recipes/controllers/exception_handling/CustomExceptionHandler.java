package com.recipes.controllers.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.recipes.services.exception.RecipeNotFoundException;

import lombok.Builder;
import lombok.Data;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    // 400
    @ExceptionHandler({ IllegalAccessException.class, IllegalArgumentException.class })
    public ResponseError handle400(final Throwable exception) {
        return ResponseError.builder().message(exception.getMessage()).status(HttpStatus.BAD_REQUEST).build();
    }

    // 403
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseError handle403(final Throwable exception) {
        return ResponseError.builder().message(exception.getMessage()).status(HttpStatus.UNAUTHORIZED).build();
    }

    // 404
    @ExceptionHandler({ RecipeNotFoundException.class })
    public ResponseError handle404(final Throwable exception) {
        return ResponseError.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND).build();
    }

    // 500
    @ExceptionHandler({ Exception.class })
    public ResponseError handle500(final Throwable exception) {
        return ResponseError.builder().message(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @Data
    @Builder
    private static class ResponseError {
        private HttpStatus status;
        private String message;
    }
}
