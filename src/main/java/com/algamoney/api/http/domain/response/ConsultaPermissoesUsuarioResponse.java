package com.algamoney.api.http.domain.response;

import com.algamoney.api.database.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ConsultaPermissoesUsuarioResponse {
    private Usuario usuario;

}