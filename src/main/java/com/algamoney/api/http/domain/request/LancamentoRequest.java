package com.algamoney.api.http.domain.request;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class LancamentoRequest {
    @NotBlank(message = "Descrição é obrigatório")
    private String descricao;

    //@NotNull(message = "Data do pagamento é obrigatório")
    //private LocalDateTime dataPagamento;

    //@NotNull(message = "Data do vencimento é obrigatório")
    //private LocalDateTime dataVencimento;

    @NotNull(message = "Valor é obrigatório")
    private BigDecimal valor;

    @NotNull(message = "Tipo do lançamento é obrigatório")
    private TipoLancamento tipoLancamento;

    @NotBlank(message = "Observação é obrigatório")
    private String observacao;

    @NotNull(message = "Categoria é obrigatória")
    private CategoriaRequest categoria;

    @NotNull(message = "Pessoa é obrigatório")
    private PessoaRequest pessoa;
}
