package com.algamoney.api.http.domain.request;

import com.algamoney.api.database.entity.enumeration.CategoriaTelefone;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;

@Getter
@Setter
public class TelefoneRequest {
    @Size(min = 11, message = "Telefone deve conter 11 digitos")
    private String numero;
    private String ramal;
    private CategoriaTelefone categoriaTelefone;
}
