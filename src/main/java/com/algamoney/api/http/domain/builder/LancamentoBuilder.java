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
        BeanUtils.copyProperties(lancamento, lancamentoDTO);
        lancamentoDTO.setCategoria(categoriaDTO);
        lancamentoDTO.setPessoa(pessoaBuilder.buildPessoaDTO(lancamento.getPessoa()));
        return lancamentoDTO;
    }
}
