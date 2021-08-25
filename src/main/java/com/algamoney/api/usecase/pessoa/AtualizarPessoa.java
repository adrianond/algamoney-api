package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.Telefone;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import com.algamoney.api.http.domain.request.AtualizarPessoaRequest;
import com.algamoney.api.http.domain.request.TelefoneRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
@Transactional
public class AtualizarPessoa {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final PessoaBuilder pessoaBuilder;

    public PessoaDTO executar(Long id, AtualizarPessoaRequest request) {
        Pessoa pessoa = pessoaRepositoryFacade.findById(id);
        BeanUtils.copyProperties(request.getEndereco(), pessoa.getEndereco());
        BeanUtils.copyProperties(request, pessoa, "id", "dataCadastro", "dataAtualizacao", "telefones");
        if (!CollectionUtils.isEmpty(request.getTelefones()))
            buildTelefones(pessoa, request.getTelefones());
        pessoa.setDataAtualizacao(LocalDateTime.now());
        return pessoaBuilder.buildPessoaDTO(pessoa);
    }

    private void buildTelefones(Pessoa pessoa, List<TelefoneRequest> telefones) {
        pessoa.getTelefones().clear();
        telefones.stream()
                .map(this::buildTelefone)
                .forEach(telefone -> pessoa.adicionaTelefone(telefone));
    }

    private Telefone buildTelefone(TelefoneRequest telefoneRequest) {
        Telefone telefone = new Telefone();
        telefone.setCategoria(telefoneRequest.getCategoriaTelefone());
        telefone.setNumero(telefoneRequest.getNumero());
        if (telefoneRequest.getRamal() != null)
            telefone.setRamal(telefoneRequest.getRamal());
        return telefone;
    }


    public PessoaDTO executar(Long id, boolean ativo) {
        Pessoa pessoa = pessoaRepositoryFacade.findById(id);
        pessoa.setAtivo(ativo);
        return pessoaBuilder.buildPessoaDTO(pessoa);
    }
}
