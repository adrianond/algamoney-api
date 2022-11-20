package com.algamoney.api.exception;

public class CargaPessoaException extends RuntimeException {

    public CargaPessoaException(String message, Exception e) {
        super(message);
    }
}
