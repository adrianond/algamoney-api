package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.CargaPessoaDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.stream.Collectors;

@Component
@Slf4j
@Transactional
@AllArgsConstructor
public class PersistirCargaPessoa {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;

    public void executar(Collection<CargaPessoaDTO> pessoaDTOCollection) {

        pessoaRepositoryFacade.saveAll(pessoaDTOCollection.stream()
                .map(this::buildListPessoa)
        .collect(Collectors.toList()));

    }

    private Pessoa buildListPessoa(CargaPessoaDTO cargaPessoaDTO) {
        Pessoa pessoa = new Pessoa();
        pessoa.setDataCadastro(LocalDateTime.now());
        BeanUtils.copyProperties(cargaPessoaDTO, pessoa);

        return pessoa;
    }
}
