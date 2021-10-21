package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import com.algamoney.api.http.domain.request.PessoaRequest;
import com.algamoney.api.usecase.endereco.CadastrarEndereco;
import com.algamoney.api.usecase.telefone.CadastrarTelefone;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
@Transactional
public class CadastrarPessoa {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final PessoaBuilder pessoaBuilder;
    private final CadastrarEndereco cadastrarEndereco;
    private final CadastrarTelefone cadastrarTelefone;

    public PessoaDTO salvar(PessoaRequest request) {
        Pessoa pessoa = new Pessoa();
        pessoa.setNome(request.getNome());
        pessoa.setAtivo(request.isAtivo());
        pessoa.setDataCadastro(LocalDateTime.now());
        return executar(pessoa, request);
    }

    public PessoaDTO atualizar(Long idPessoa, PessoaRequest request) {
        Pessoa pessoa = pessoaRepositoryFacade.findById(idPessoa);
        pessoa.setNome(request.getNome());
        pessoa.setAtivo(request.isAtivo());
        pessoa.setDataAtualizacao(LocalDateTime.now());
        return executar(pessoa, request);
    }

    public PessoaDTO executar(Pessoa pessoa, PessoaRequest request) {
        cadastrarEndereco.executar(pessoa, request.getEndereco());
        Pessoa p = pessoaRepositoryFacade.add(pessoa);
        cadastrarTelefone.executar(p, request.getTelefones());
        return pessoaBuilder.buildPessoaDTO(p);
    }
}
