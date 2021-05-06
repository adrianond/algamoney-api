package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ExcluirPessoa {
    private PessoaRepositoryFacade pessoaRepositoryFacade;

    public void executar(Long id) {
        Pessoa pessoa = pessoaRepositoryFacade.findById(id);
        pessoaRepositoryFacade.delete(pessoa);
    }
}
