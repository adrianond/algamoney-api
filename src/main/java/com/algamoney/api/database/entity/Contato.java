package com.algamoney.api.database.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Table(name = "contato_pessoa")
@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Contato implements Serializable {
    @Id
    /*@SequenceGenerator(name = "SEQ_CONTATO", sequenceName = "SEQ_CONTATO", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTATO")*/
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "email")
    private String email;

    @Column(name = "telefone")
    private String telefone;

    @ManyToOne(targetEntity = Pessoa.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id")
    private Pessoa pessoa;
}
