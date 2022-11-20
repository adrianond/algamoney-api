package com.algamoney.api.database.entity.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public enum CategoriaTelefone {
    RESIDENCIAL(1), COMERCIAL(2), CELULAR(3);

    private Integer categoria;

}
