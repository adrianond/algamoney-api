package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.QPessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import com.querydsl.core.BooleanBuilder;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConsultaPessoas {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final PessoaBuilder pessoaBuilder;

    public List<PessoaDTO> executar() {
        return pessoaRepositoryFacade.findAll().stream()
                .map(pessoa -> pessoaBuilder.buildPessoaDTO(pessoa))
                .collect(Collectors.toList());
    }

    public Page<PessoaDTO> executarComPaginacao(Pageable pageable, String nome) {
        BooleanBuilder predicate = new BooleanBuilder();

        if (StringUtils.hasText(nome))
            predicate.and(QPessoa.pessoa.nome.equalsIgnoreCase(nome));

        return pessoaRepositoryFacade.findAll(predicate, pageable)
                .map(pessoa -> pessoaBuilder.buildPessoaDTO(pessoa));
    }
}
