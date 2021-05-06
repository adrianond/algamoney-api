package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
}
