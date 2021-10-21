package com.algamoney.api.http.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TelefoneIdDTO {
    private Long pessoa;
    private int sequencia;

    public TelefoneIdDTO(Long pessoa, int sequencia) {
        this.pessoa = pessoa;
        this.sequencia = sequencia;
    }
}
