package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Pessoa;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PessoaRepositoryFacade {
    Pessoa add(Pessoa pessoa);

    Pessoa findById(Long id);

    List<Pessoa> findAll();

    void delete(Pessoa pessoa);

    Page<Pessoa> findAll(Predicate predicate, Pageable pageable);

    void saveAll(List<Pessoa> cargaPessoaDTOList);
}
