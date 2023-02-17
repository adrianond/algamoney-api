package com.algamoney.api.http.domain.builder;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.algamoney.api.http.domain.LancamentoDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LancamentoBuilder {
    private final PessoaBuilder pessoaBuilder;

    public LancamentoDTO build(Lancamento lancamento) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        LancamentoDTO lancamentoDTO = new LancamentoDTO();
        BeanUtils.copyProperties(lancamento.getCategoria(), categoriaDTO);
        lancamentoDTO.setValor(lancamento.getValor());
        lancamentoDTO.setTipo(lancamento.getTipoLancamento());
        lancamentoDTO.setDescricao(lancamento.getDescricao());
        lancamentoDTO.setId(lancamento.getId());
        lancamentoDTO.setObservacao(lancamento.getObservacao());
        lancamentoDTO.setDataVencimento(lancamento.getDataVencimento());
        lancamentoDTO.setDataRecebimentoPagamento(lancamento.getDataPagamento());
        //lancamentoDTO.setAnexo(lancamento.getAnexo());
        lancamentoDTO.setUrl(lancamento.getUrl());
        lancamentoDTO.setCategoria(categoriaDTO);
        lancamentoDTO.setPessoa(pessoaBuilder.buildPessoaDTO(lancamento.getPessoa()));
        return lancamentoDTO;
    }
}
