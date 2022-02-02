package com.algamoney.api.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
@AllArgsConstructor
@Slf4j
public class AlgamoneyExceptionHandler extends ResponseEntityExceptionHandler {
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex, WebRequest request) {
        ResponseStatus annotationResponse = getResponseAnnotation(ex.getClass());
        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String status = httpStatus.name();
        int httpStatusCode = httpStatus.value();

        if(annotationResponse != null) {
            httpStatus = annotationResponse.value();
            if(annotationResponse.reason() == null || annotationResponse.reason().isEmpty())
                status = httpStatus.name();
            else
                status = annotationResponse.reason();
            httpStatusCode = annotationResponse.value().value();
        }else {
            log.error(ex.getMessage(), ex);
        }
        return new ResponseEntity<>(new ErrorResponse(status, ex.getMessage(), httpStatusCode), httpStatus);
    }

    private ResponseStatus getResponseAnnotation(Class<?> exceptionClass) {
        return AnnotationUtils.findAnnotation(exceptionClass, ResponseStatus.class);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors())
            errors.add(error.getDefaultMessage());

        for (ObjectError error : ex.getBindingResult().getGlobalErrors())
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        return handleExceptionInternal(ex, errors, headers, BAD_REQUEST, request);
    }

    @Getter
    @NoArgsConstructor
    public static class ErrorResponse {
        private String status;
        private String mensagem;
        private int httpStatusCode;

        public ErrorResponse(String status, String mensagem, int httpStatusCode) {
            this.status = status;
            this.mensagem = mensagem;
            this.httpStatusCode = httpStatusCode;
        }
    }
}
