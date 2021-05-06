package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.exception.LancamentoNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LancamentoRepositoryFacadeImpl implements LancamentoRepositoryFacade {
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
}
