package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class AtualizarPessoa {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final PessoaBuilder pessoaBuilder;

    public PessoaDTO executar(Long id, boolean ativo) {
        Pessoa pessoa = pessoaRepositoryFacade.findById(id);
        pessoa.setAtivo(ativo);
        return pessoaBuilder.buildPessoaDTO(pessoa);
    }
}
