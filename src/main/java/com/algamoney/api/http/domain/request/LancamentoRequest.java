package com.algamoney.api.http.domain.request;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class LancamentoRequest {
    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "Data do recebimento/pagamento é obrigatório")
    private LocalDate dataRecebimentoPagamento;

    @DateTimeFormat(pattern = "dd-MM-yyyy", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @NotNull(message = "Data do vencimento é obrigatório")
    private LocalDate dataVencimento;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "Tipo do lançamento é obrigatório")
    private TipoLancamento tipo;

    private String observacao;

    @NotNull(message = "Categoria é obrigatória")
    private Long  idCategoria;

    @NotNull(message = "Pessoa é obrigatório")
    private Long  idPessoa;
}
