package com.algamoney.api.database.repository;

import com.algamoney.api.database.entity.AlgamoneyViewConsulta;
import com.algamoney.api.http.domain.request.AlgamoneyFilterRequest;
import com.algamoney.api.jdbc.JdbcPageable;
import com.algamoney.api.jdbc.JdbcReport;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Component
public class ConsultaViewAlgamoneyJdbc extends JdbcReport<AlgamoneyViewConsulta, AlgamoneyFilterRequest> {

    public ConsultaViewAlgamoneyJdbc(JdbcPageable jdbcPageable) {
        super(jdbcPageable, AlgamoneyViewConsulta.class);
    }

    @Override
    protected String query (AlgamoneyFilterRequest filters) {

       return joinln(
      "select view.nome nome, ",
               "       view.logradouro logradouro, ",
               "       view.descricao descricao, ",
               "       view.bairro bairro, ",
               "       view.tipo_lancamento tipoLancamento, ",
               "       view.nome_categoria nomeCategoria",
               "from algamoney_consulta view, ",
               isNotBlank(filters.getNome()) ? "and upper(view.nome) like upper('%" + filters.getNome() + "%')" : null,
               isNotBlank(filters.getLogradouro()) ? "and upper(view.logradouro) like upper('%" + filters.getLogradouro() + "%')" : null,
               isNotBlank(filters.getDescricao()) ? "and upper(view.descricao) like upper('%" + filters.getDescricao() + "%')" : null,
               isNotBlank(filters.getBairro()) ? "and upper(view.bairro) like upper('%" + filters.getBairro() + "%')" : null,
               isNotBlank(filters.getTipoLancamento()) ? "and upper(view.tipo_lancamento) like upper('%" + filters.getTipoLancamento() + "%')" : null,
               isNotBlank(filters.getNomeCategoria()) ? "and upper(view.nome_categoria) like upper('%" + filters.getNomeCategoria() + "%')" : null,
               "group by view.nome, view.logradouro, view.bairro, view.tipo_lancamento, view.nome_categoria");
        }

    public static String joinln(final String... strings) {
        Validate.notNull(strings, "Object varargs must not be null");
        return Arrays.stream(strings)
                .filter(Objects::nonNull)
                .collect(Collectors.joining(System.lineSeparator()));
    }
}
