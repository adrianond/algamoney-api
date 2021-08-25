package com.algamoney.api.database.repository;

import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.ResumoLancamentoDTO;
import com.algamoney.api.http.domain.request.LancamentoFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface LancamentoRepositoryFacade {
    Page<LancamentoDTO> filtrar(LancamentoFilter lancamentoFilter, Pageable pageable);
    Page<ResumoLancamentoDTO> resumir(LancamentoFilter lancamentoFilter, Pageable pageable);
}
