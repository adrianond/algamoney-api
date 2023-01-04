package com.algamoney.api.config.security;

import com.algamoney.api.exception.UsuarioAcessoNegadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) {
        log.error("Usuário sem acesso {} {} ", e.getMessage(), e);
        throw new UsuarioAcessoNegadoException("Acesso Negado.");

    }
}
