package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.ResumoLancamentoDTO;
import com.algamoney.api.http.domain.request.LancamentoFilter;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


public interface LancamentoRepositoryFacade {
    Page<LancamentoDTO> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
    Page<ResumoLancamentoDTO> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
    Lancamento save(Lancamento lancamento);
    Lancamento findById(Long id);
    void delete(Lancamento lancamento);
    List<Lancamento> findAll();
    Page<Lancamento> findAll(Predicate predicate, Pageable pageable);
    List<Lancamento> findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate data);
}
