package com.algamoney.api.database.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum CategoriaTelefone {
    RESIDENCIAL(1), COMERCIAL(2), CELULAR(3);

    private Integer categoria;
}
