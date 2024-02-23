package com.algamoney.api.http.domain.request;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class LancamentoRequest {

    @ApiModelProperty(
            value = "Indentificador unico do lancamento",
            hidden = true
    )
    private Long id;

    @ApiModelProperty(
            value = "Descrição do lançamento",
            example = "casa",
            dataType = "String"
    )
    private String descricao;

    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "Data do vencimento é obrigatório")
    @ApiModelProperty(
            value = "Data de vencimento",
            required = true,
            dataType = "LocalDate",
            example = "02-01-2001"
    )
    private LocalDate dataVencimento;

    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @ApiModelProperty(
            value = "Data de recebimento/pagamento",
            dataType = "LocalDate",
            example = "02-01-2001"
    )
    private LocalDate dataRecebimentoPagamento;

   @NotNull(message = "Valor é obrigatório")
    @ApiModelProperty(
            value = "Valor do lançamento",
            required = true,
            dataType = "BigDecimal",
            example = "100,00"
    )
    private BigDecimal valor;

    @NotNull(message = "Tipo do lançamento é obrigatório")
    @ApiModelProperty(
            value = "Tipo de lançamento",
            required = true,
            dataType = "String",
            example = "DESPESA"
    )
    private TipoLancamento tipo;

    @ApiModelProperty(
            value = "Observação a respeito do lançamento",
            example = "Teste"
    )
    private String observacao;

    @NotNull(message = "Id da categoria é obrigatória")
    @ApiModelProperty(
            value = "Categoria do lançamento",
            required = true
    )
    private Long idCategoria;

   /* @ApiModelProperty(
            value = "Nome do arquivo a ser salvo por definitivo na AWS"
    )
    private String anexo;*/

    @NotNull(message = "Id pessoa é obrigatório")
    @ApiModelProperty(
            value = "Pessoa que pertence o lançamento",
            required = true
    )
    private Long idPessoa;
}
