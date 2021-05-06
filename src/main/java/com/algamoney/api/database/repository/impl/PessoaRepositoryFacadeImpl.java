package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.exception.PessoaNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PessoaRepositoryFacadeImpl implements PessoaRepositoryFacade {
    private final PessoaRepository repository;

    @Override
    public Pessoa add(Pessoa pessoa) {
        return repository.save(pessoa);
    }

    @Override
    public Pessoa findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new PessoaNotFoundException("Pessoa não encontrada"));
    }

    @Override
    public void delete(Pessoa pessoa) {
        repository.delete(pessoa);
    }
}
