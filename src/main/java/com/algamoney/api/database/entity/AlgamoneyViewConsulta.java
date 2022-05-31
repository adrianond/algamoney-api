package com.algamoney.api.database.entity;

import lombok.Data;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "algamoney_consulta")
@Immutable
@Data
public class AlgamoneyViewConsulta implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String nome;

    @Column
    private String logradouro;

    @Column
    private String bairro;

    @Column
    private String descricao;

    @Column
    private String tipoLancamento;

    @Column
    private String nomeCategoria;
}
