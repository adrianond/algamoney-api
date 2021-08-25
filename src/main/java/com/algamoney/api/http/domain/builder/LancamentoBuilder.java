package com.algamoney.api.http.domain.builder;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.algamoney.api.http.domain.LancamentoDTO;
import com.algamoney.api.http.domain.ResumoLancamentoDTO;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import static com.algamoney.api.util.DateUtil.formatLocalDate;
import static com.algamoney.api.util.DateUtil.formatLocalDateTime;

@Component
@AllArgsConstructor
public class LancamentoBuilder {
    private final PessoaBuilder pessoaBuilder;

    public LancamentoDTO build(Lancamento lancamento) {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        LancamentoDTO lancamentoDTO = new LancamentoDTO();

        BeanUtils.copyProperties(lancamento.getCategoria(), categoriaDTO);
        lancamentoDTO.setValor(lancamento.getValor());
        lancamentoDTO.setTipoLancamento(lancamento.getTipoLancamento());
        lancamentoDTO.setDescricao(lancamento.getDescricao());
        lancamentoDTO.setId(lancamento.getId());
        lancamentoDTO.setObservacao(lancamento.getObservacao());
        lancamentoDTO.setDataVencimento(formatLocalDateTime(lancamento.getDataVencimento()));
        lancamentoDTO.setDataPagamento(formatLocalDateTime(lancamento.getDataPagamento()));
        lancamentoDTO.setCategoria(categoriaDTO);
        lancamentoDTO.setPessoa(pessoaBuilder.buildPessoaDTO(lancamento.getPessoa()));
        return lancamentoDTO;
    }
/*
    public ResumoLancamentoDTO build(ResumoLancamentoDTO resumoLancamentoDTO) {
    }*/
}
