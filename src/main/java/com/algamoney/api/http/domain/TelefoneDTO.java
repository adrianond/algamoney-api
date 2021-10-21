package com.algamoney.api.http.domain;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TelefoneDTO {
    private TelefoneIdDTO id;
    private String numero;
    private String ramal;
}
