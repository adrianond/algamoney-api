package com.algamoney.api.usecase;

import com.algamoney.api.database.entity.AlgamoneyViewConsulta;
import com.algamoney.api.database.entity.QAlgamoneyViewConsulta;
import com.algamoney.api.database.repository.AlgamoneyViewConsultaRepositoryFacade;
import com.algamoney.api.database.repository.ConsultaViewAlgamoneyJdbc;
import com.algamoney.api.http.domain.request.AlgamoneyFilterRequest;
import com.algamoney.api.http.domain.response.ConsultaViewAlgamoneyPageResponse;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Component
@AllArgsConstructor
public class ConsultaViewAlgamoney {
    private final AlgamoneyViewConsultaRepositoryFacade facade;
    private final ConsultaViewAlgamoneyJdbc consultaViewAlgamoneyJdbc;

    public List<AlgamoneyViewConsulta> executar() {
        return facade.findAll();
    }

    public Page<AlgamoneyViewConsulta> executar(Pageable pageable, String nome, String logradouro, String descricao,
                                                            String  bairro, String tipoLancamento, String  nomeCategoria) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (StringUtils.hasText(nome))
            predicate.and(QAlgamoneyViewConsulta.algamoneyViewConsulta.nome.containsIgnoreCase(nome));

        if (StringUtils.hasText(logradouro))
            predicate.and(QAlgamoneyViewConsulta.algamoneyViewConsulta.logradouro.containsIgnoreCase(logradouro));

        if (StringUtils.hasText(descricao))
            predicate.and(QAlgamoneyViewConsulta.algamoneyViewConsulta.descricao.containsIgnoreCase(descricao));

        if (StringUtils.hasText(bairro))
            predicate.and(QAlgamoneyViewConsulta.algamoneyViewConsulta.bairro.containsIgnoreCase(bairro));

        if (StringUtils.hasText(tipoLancamento))
            predicate.and(QAlgamoneyViewConsulta.algamoneyViewConsulta.tipoLancamento.containsIgnoreCase(tipoLancamento));

        if (StringUtils.hasText(nomeCategoria))
            predicate.and(QAlgamoneyViewConsulta.algamoneyViewConsulta.nomeCategoria.containsIgnoreCase(nomeCategoria));

        return facade.findAll(predicate, pageable);
    }

    public ConsultaViewAlgamoneyPageResponse executar(AlgamoneyFilterRequest filters, Pageable page) {
        return new ConsultaViewAlgamoneyPageResponse(consultaViewAlgamoneyJdbc.executar(filters, page));
    }
}
