package com.algamoney.api.http.domain;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class LancamentoDTO {
    private Long id;
    private String descricao;
    private LocalDateTime dataVencimento;
    private LocalDateTime dataPagamento;
    private BigDecimal valor;
    private TipoLancamento tipoLancamento;
    private String observacao;
    private CategoriaDTO categoria;
    private PessoaDTO pessoa;
}
