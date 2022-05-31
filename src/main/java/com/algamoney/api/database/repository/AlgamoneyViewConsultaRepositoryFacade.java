package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.AlgamoneyViewConsulta;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AlgamoneyViewConsultaRepositoryFacade {
    List<AlgamoneyViewConsulta> findAll();

    Page<AlgamoneyViewConsulta> findAll(Predicate predicate, Pageable pageable);
}
