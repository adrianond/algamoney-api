package com.algamoney.api.http.domain;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LancamentoDTO {

    private Long id;
    private String descricao;

    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataVencimento;

    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dataRecebimentoPagamento;

    private BigDecimal valor;
    private TipoLancamento tipo;
    private String observacao;
    private CategoriaDTO categoria;
    private String anexo;
    private String url;
    private PessoaDTO pessoa;
}
