package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Lancamento;

import java.util.List;

public interface LancamentoRepositoryFacade {
    Lancamento save(Lancamento lancamento);
    Lancamento findById(Long id);
    void delete(Lancamento lancamento);
    List<Lancamento> findAll();
}
