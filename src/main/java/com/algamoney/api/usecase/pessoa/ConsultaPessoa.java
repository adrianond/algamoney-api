package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultaPessoa {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final PessoaBuilder pessoaBuilder;

    public PessoaDTO executar(Long id) {
      return pessoaBuilder.buildPessoaDTO(pessoaRepositoryFacade.findById(id));
    }
}
