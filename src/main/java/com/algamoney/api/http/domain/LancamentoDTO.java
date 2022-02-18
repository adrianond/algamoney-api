package com.algamoney.api.http.domain;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {
    private Long id;
    private String descricao;
    private String dataVencimento;
    private String dataRecebimentoPagamento;
    private BigDecimal valor;
    private TipoLancamento tipo;
    private String observacao;
    private CategoriaDTO categoria;
    private PessoaDTO pessoa;
}
