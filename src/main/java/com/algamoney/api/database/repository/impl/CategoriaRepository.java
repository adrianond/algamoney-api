package com.algamoney.api.database.repository.impl;

import com.algamoney.api.database.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    public default String carregaNovaOperacao(DataSource dataSource, Integer p_emp, Integer p_operacao, Integer p_lojista,
                                              String p_fila_interna, Integer p_score, Integer p_proposta) throws SQLException {
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

            cs.setInt(6, p_proposta);

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
