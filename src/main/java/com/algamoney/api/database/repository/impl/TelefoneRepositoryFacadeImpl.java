package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Telefone;
import com.algamoney.api.database.repository.TelefoneRepositoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TelefoneRepositoryFacadeImpl implements TelefoneRepositoryFacade {
    private final TelefoneRepository repository;


    @Override
    public List<Telefone> saveAll(List<Telefone> telefones) {
        return repository.saveAll(telefones);
    }
}
