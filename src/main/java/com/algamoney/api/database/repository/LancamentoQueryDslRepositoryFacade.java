package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Lancamento;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface LancamentoQueryDslRepositoryFacade {
    Lancamento save(Lancamento lancamento);
    Lancamento findById(Long id);
    void delete(Lancamento lancamento);
    List<Lancamento> findAll();
    Page<Lancamento> findAll(Predicate predicate, Pageable pageable);
}
