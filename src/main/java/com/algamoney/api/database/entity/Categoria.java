package com.algamoney.api.database.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id

    @SequenceGenerator(name = "SEQ_CATEGORIA", sequenceName = "SEQ_CATEGORIA", allocationSize = 1)
    @GeneratedValue(generator = "SEQ_CATEGORIA",strategy = GenerationType.SEQUENCE)
    //@GeneratedValue(strategy=GenerationType.AUTO) mysql
    private Long id;

    @Column(nullable = false)
    private String nome;

    public Categoria(String nome) {
        this.nome = nome;
    }
}
