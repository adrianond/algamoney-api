package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.repository.LancamentoQueryDslRepositoryFacade;
import com.algamoney.api.exception.LancamentoNotFoundException;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Service
@AllArgsConstructor
public class LancamentoQueryDslRepositoryFacadeImpl implements LancamentoQueryDslRepositoryFacade {
    @PersistenceContext
    private EntityManager manager;
    private final LancamentoRepository repository;

    @Override
    public Lancamento save(Lancamento lancamento) {
        return repository.save(lancamento);
    }

    @Override
    public Lancamento findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new LancamentoNotFoundException("Lançamento não encontrado"));
    }

    @Override
    public void delete(Lancamento lancamento) {
        repository.delete(lancamento);
    }

    @Override
    public List<Lancamento> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<Lancamento> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate,pageable);
    }
}
