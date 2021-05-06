package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.embedded.Endereco;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import com.algamoney.api.http.domain.request.EnderecoRequest;
import com.algamoney.api.http.domain.request.PessoaRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class AdicionaPessoa {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final PessoaBuilder pessoaBuilder;

    public PessoaDTO executar(PessoaRequest request) {
        return pessoaBuilder.buildPessoaDTO(pessoaRepositoryFacade.add(buildEntity(request)));
    }

    private Pessoa buildEntity(PessoaRequest request) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());
        pessoa.setAtivo(request.isAtivo());
        pessoa.setEndereco(buildEndereco(request.getEndereco()));
        pessoa.setDataCadastro(LocalDateTime.now());
        return pessoa;
    }

    private Endereco buildEndereco(EnderecoRequest request) {
        if (request == null)
            return null;
        Endereco endereco = new Endereco();
        endereco.setBairro(request.getBairro());
        endereco.setCep(request.getCep());
        endereco.setCidade(request.getCidade());
        endereco.setComplemento(request.getComplemento());
        endereco.setEstado(request.getEstado());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setNumero(request.getNumero());
        return endereco;
    }
}
