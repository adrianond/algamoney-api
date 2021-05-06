package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.embedded.Endereco;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import com.algamoney.api.http.domain.request.EnderecoRequest;
import com.algamoney.api.http.domain.request.LancamentoRequest;
import com.algamoney.api.http.domain.request.PessoaRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class SalvarLancamento {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;
    private final LancamentoBuilder lancamentoBuilder;

    public LancamentoDTO executar(LancamentoRequest request) {
        Lancamento lancamento = lancamentoRepositoryFacade.save(build(request));
        return lancamentoBuilder.build(lancamento);
    }

    private Lancamento build(LancamentoRequest request) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataPagamento(LocalDateTime.now());
        lancamento.setDataVencimento(LocalDateTime.now());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setObservacao(request.getObservacao());
        lancamento.setTipoLancamento(request.getTipoLancamento());
        lancamento.setValor(request.getValor());
        lancamento.setCategoria(categoriaRepositoryFacade.findById(request.getCategoria().getId()));
        lancamento.setPessoa(buildPessoa(request.getPessoa()));
        return lancamento;
    }

    private Pessoa buildPessoa(PessoaRequest pessoaRequest) {
        Pessoa pessoa = new Pessoa();
        pessoa.setAtivo(pessoaRequest.isAtivo());
        pessoa.setNome(pessoaRequest.getNome());
        pessoa.setDataCadastro(LocalDateTime.now());
        pessoa.setEndereco(buildEndereco(pessoaRequest.getEndereco()));
        return pessoa;
    }

    private Endereco buildEndereco(EnderecoRequest request) {
        if (request == null)
            return null;
        Endereco endereco = new Endereco();
        endereco.setNumero(request.getNumero());
        endereco.setLogradouro(request.getLogradouro());
        endereco.setEstado(request.getEstado());
        endereco.setComplemento(request.getComplemento());
        endereco.setCidade(request.getCidade());
        endereco.setCep(request.getCep());
        endereco.setBairro(request.getBairro());
        return endereco;
    }
}
