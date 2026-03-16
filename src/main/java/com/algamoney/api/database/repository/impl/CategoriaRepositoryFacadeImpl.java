package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.exception.CategoriaNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoriaRepositoryFacadeImpl implements CategoriaRepositoryFacade {
    private final CategoriaRepository repository;
    private final DataSource dataSource;

    @Override
    public Categoria findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CategoriaNotFoundException(String.format("Categoria não encontrada para o id %s:", id)));
    }

    @Override
    public Categoria save(Categoria categoria) {
        return repository.save(categoria);
    }

    @Override
    public List<Categoria> findAll() {
        return repository.findAll();
    }


    @Override
    public void delete(Categoria categoria) {
        repository.delete(categoria);
    }

    @Override
    public String findAllV2(Integer p_emp, Integer p_operacao, Integer p_lojista,
                                              String p_fila_interna, Integer p_score, Long p_proposta) throws SQLException {

        String resultadoJson = null;
        String sql = "{ call carregaNovaOperacao(?, ?, ?, ?, ?, ?, ?) }";

        try (Connection conn = dataSource.getConnection();
             CallableStatement cs = conn.prepareCall(sql)) {

            cs.setInt(1, p_emp);
            cs.setInt(2, p_operacao);
            cs.setInt(3, p_lojista);

            if (p_fila_interna != null) {
                cs.setString(4, p_fila_interna);
            } else {
                cs.setNull(4, Types.VARCHAR);
            }

            if (p_score != null) {
                cs.setInt(5, p_score);
            } else {
                cs.setNull(5, Types.INTEGER);
            }

            cs.setLong(6, p_proposta);

            cs.registerOutParameter(7, Types.CLOB);

            cs.execute();

            Clob clob = cs.getClob(7);
            if (clob != null) {
                resultadoJson = clob.getSubString(1, (int) clob.length());
            }
        }
        return resultadoJson;
    }
}
