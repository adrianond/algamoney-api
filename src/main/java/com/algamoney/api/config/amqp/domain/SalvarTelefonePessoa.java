package com.algamoney.api.config.amqp.domain;


import com.algamoney.api.http.domain.request.TelefoneRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SalvarTelefonePessoa implements Serializable {
    private final Long id;
    private List<TelefoneRequest> telefones;

    public SalvarTelefonePessoa(Long id, List<TelefoneRequest> telefones) {
        this.id = id;
        this.telefones = telefones;
    }
}
