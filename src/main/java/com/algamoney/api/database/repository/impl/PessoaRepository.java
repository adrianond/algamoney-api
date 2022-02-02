package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long>, QuerydslPredicateExecutor<Pessoa> {
}
