package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Telefone;

import java.util.List;

public interface TelefoneRepositoryFacade {
    List<Telefone> saveAll(List<Telefone> telefones);
}
