package com.algamoney.api.database.entity.embedded;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class TelefoneId implements Serializable {

    @Column(name = "id_pessoa")
    private Long pessoa;

    @Column(name = "sequencia")
    private int sequencia;

}
