package com.algamoney.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UsuarioAcessoNegadoException extends RuntimeException {

    public UsuarioAcessoNegadoException(String message) {
        super(message);
    }
}
