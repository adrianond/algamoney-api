package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.exception.PessoaNotFoundException;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<Pessoa> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Pessoa pessoa) {
        repository.delete(pessoa);
    }

    @Override
    public Page<Pessoa> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }

    @Override
    public void saveAll(List<Pessoa> cargaPessoaDTOList) {
        repository.saveAll(cargaPessoaDTOList);
    }
}
