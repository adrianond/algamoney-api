package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.database.repository.LancamentoQueryDslRepositoryFacade;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import com.algamoney.api.http.domain.request.LancamentoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class SalvarLancamento {
    private final LancamentoQueryDslRepositoryFacade lancamentoQueryDslRepositoryFacade;
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final LancamentoBuilder lancamentoBuilder;

    public LancamentoDTO executar(LancamentoRequest request) {
        Lancamento lancamento = lancamentoQueryDslRepositoryFacade.save(build(request));
        return lancamentoBuilder.build(lancamento);
    }

    private Lancamento build(LancamentoRequest request) {
        Lancamento lancamento = new Lancamento();
        lancamento.setDataPagamento(request.getDataPagamento());
        lancamento.setDataVencimento(request.getDataVencimento());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setObservacao(request.getObservacao());
        lancamento.setTipoLancamento(request.getTipoLancamento());
        lancamento.setValor(request.getValor());
        lancamento.setCategoria(categoriaRepositoryFacade.findById(request.getCodigoCategoria()));
        lancamento.setPessoa(buildPessoa(request.getCodigoPessoa()));
        return lancamento;
    }

    private Pessoa buildPessoa(Long codigoPessoa) {
       return pessoaRepositoryFacade.findById(codigoPessoa);
    }
}
