package com.algamoney.api.usecase.lancamento;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.builder.LancamentoBuilder;
import com.algamoney.api.http.domain.mapper.LancamentoMapper;
import com.algamoney.api.http.domain.request.LancamentoRequest;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
@Transactional
public class PersistiLancamento {
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final LancamentoBuilder lancamentoBuilder;
    //private final EnviarArquivoS3 enviarArquivoS3;

    public LancamentoDTO executar(LancamentoRequest request) {
        Lancamento lancamento = lancamentoRepositoryFacade.save(build(null, request));

        //LancamentoMapper mapper = Mappers.getMapper(LancamentoMapper.class);
        //return mapper.lancamentoEntityToLancamentoDto(lancamento);
        return lancamentoBuilder.build(lancamento);
    }

    private Lancamento build(Lancamento lancamento, LancamentoRequest request) {
        if (null == lancamento)
            lancamento = new Lancamento();

        lancamento.setDataPagamento(request.getDataRecebimentoPagamento());
        lancamento.setDataVencimento(request.getDataVencimento());
        lancamento.setDescricao(request.getDescricao());
        lancamento.setObservacao(request.getObservacao());
        lancamento.setTipoLancamento(request.getTipo());
        lancamento.setValor(request.getValor());

       /* if (StringUtils.hasText(request.getAnexo()) && !StringUtils.hasText(lancamento.getAnexo()))
            enviarArquivoS3.salvar(request.getAnexo());
        else if (!StringUtils.hasText(request.getAnexo()) && StringUtils.hasText(lancamento.getAnexo()))
            enviarArquivoS3.remover(lancamento.getAnexo());
        else if(StringUtils.hasText(request.getAnexo()) && !request.getAnexo().equals(lancamento.getAnexo()))
            enviarArquivoS3.substituir(lancamento.getAnexo(), request.getAnexo());*/

        //lancamento.setAnexo(request.getAnexo());
        lancamento.setCategoria(categoriaRepositoryFacade.findById(request.getIdCategoria()));
        lancamento.setPessoa(pessoaRepositoryFacade.findById(request.getIdPessoa()));
        return lancamento;
    }

    public void executar(Long id, LancamentoRequest request) {
        build(lancamentoRepositoryFacade.findById(id), request);
    }
}
