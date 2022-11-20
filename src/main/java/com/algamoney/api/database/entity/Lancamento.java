package com.algamoney.api.database.entity;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.listener.LancamentoAnexoListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@EntityListeners(LancamentoAnexoListener.class)
@Entity
@EqualsAndHashCode(of = "id")
@Data
@NoArgsConstructor
public class Lancamento implements Serializable {

    @Id
    @SequenceGenerator(name = "SEQ_LANCAMENTO", sequenceName = "SEQ_LANCAMENTO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LANCAMENTO")
    private Long id;

    private String descricao;

    @Column(name = "data_vencimento")
    private LocalDate dataVencimento;

    @Column(name = "data_recebimento_pagamento")
    private LocalDate dataPagamento;

    private BigDecimal valor;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tipo_lancamento")
    private TipoLancamento tipoLancamento;

    private String anexo;

    @Transient
    private String url;

    private String observacao;

    @JoinColumn(name = "id_categoria", referencedColumnName = "id", nullable = false)
    @ManyToOne(targetEntity = Categoria.class, fetch = FetchType.LAZY)
    private Categoria categoria;

    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    @ManyToOne(targetEntity = Pessoa.class, fetch = FetchType.LAZY)
    private Pessoa pessoa;

    @JsonIgnore
    public boolean isReceita() {
        return TipoLancamento.RECEITA.equals(this.tipoLancamento);
    }
}
