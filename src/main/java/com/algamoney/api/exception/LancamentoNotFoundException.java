package com.algamoney.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class LancamentoNotFoundException extends RuntimeException {

    public LancamentoNotFoundException(String message) {
        super(message);
    }
}
