package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Lancamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LancamentoRepository extends JpaRepository<Lancamento, Long> {
}
