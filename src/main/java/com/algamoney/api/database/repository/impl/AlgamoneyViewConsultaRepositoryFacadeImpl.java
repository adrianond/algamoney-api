package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.AlgamoneyViewConsulta;
import com.algamoney.api.database.repository.AlgamoneyViewConsultaRepositoryFacade;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class AlgamoneyViewConsultaRepositoryFacadeImpl implements AlgamoneyViewConsultaRepositoryFacade {
    private final AlgamoneyViewConsultaRepository repository;


    @Override
    public List<AlgamoneyViewConsulta> findAll() {
        return repository.findAll();
    }

    @Override
    public Page<AlgamoneyViewConsulta> findAll(Predicate predicate, Pageable pageable) {
        return repository.findAll(predicate, pageable);
    }
}
