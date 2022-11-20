package com.algamoney.api.config.amqp.domain;


import com.algamoney.api.http.domain.TelefoneDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SalvarTelefonePessoaMessage implements Serializable {
    private final Long id;
    private List<TelefoneDTO> telefones;

    public SalvarTelefonePessoaMessage(Long id, List<TelefoneDTO> telefones) {
        this.id = id;
        this.telefones = telefones;
    }
}
