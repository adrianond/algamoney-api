package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.Categoria;

import java.sql.SQLException;
import java.util.List;

public interface CategoriaRepositoryFacade {
    Categoria findById(Long id);
    Categoria save(Categoria categoria);
    List<Categoria> findAll();
    void delete(Categoria categoria);

    String findAllV2(Integer p_emp, Integer p_operacao, Integer p_lojista, String p_fila_interna, Integer p_score, Long p_proposta) throws SQLException;
}
