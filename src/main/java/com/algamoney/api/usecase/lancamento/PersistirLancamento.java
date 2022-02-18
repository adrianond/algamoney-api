package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import com.algamoney.api.http.domain.request.LancamentoRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class PersistirLancamento {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final LancamentoBuilder lancamentoBuilder;

    public LancamentoDTO executar(LancamentoRequest request) {
        Lancamento lancamento = lancamentoRepositoryFacade.save(build(null, request));
        return lancamentoBuilder.build(lancamento);
    }

    private Lancamento build(Lancamento lancamento, LancamentoRequest request) {
        if (null == lancamento)
            lancamento = new Lancamento();

        lancamento.setDataRecebimentoPagamento(request.getDataRecebimentoPagamento());
        lancamento.setDataVencimento(request.getDataVencimento());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setObservacao(request.getObservacao());
        lancamento.setTipoLancamento(request.getTipo());
        lancamento.setValor(request.getValor());
        lancamento.setCategoria(categoriaRepositoryFacade.findById(request.getIdCategoria()));
        lancamento.setPessoa(pessoaRepositoryFacade.findById(request.getIdPessoa()));
        return lancamento;
    }

    public void executar(Long id, LancamentoRequest request) {
        build(lancamentoRepositoryFacade.findById(id), request);
    }
}
