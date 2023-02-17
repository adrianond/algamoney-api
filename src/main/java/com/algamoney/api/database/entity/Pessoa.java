package com.algamoney.api.database.entity;

import com.algamoney.api.database.entity.embedded.Endereco;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A Pessoa
 */
@Entity
@EqualsAndHashCode(of = "id")
@Data
@NoArgsConstructor
public class Pessoa implements Serializable {
    private static final long serialVersionUID = 1L;

    /* @SequenceGenerator(name = "SEQ_PESSOA", sequenceName = "SEQ_PESSOA", allocationSize = 1)
     @GeneratedValue(generator = "SEQ_PESSOA", strategy = GenerationType.SEQUENCE)*/
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Id
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(name = "data_cadastro", nullable = false)
    LocalDateTime dataCadastro;

    @Column(name = "data_atualizacao")
    private LocalDateTime dataAtualizacao;

    @Column(nullable = false)
    private boolean ativo;

    @Embedded
    private Endereco endereco;

    @OneToMany(mappedBy = "pessoa", targetEntity = Telefone.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Telefone> telefones = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa", targetEntity = Lancamento.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lancamento> lancamentos = new ArrayList<>();

    @OneToMany(mappedBy = "pessoa", targetEntity = Contato.class, fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Contato> contatos = new ArrayList<>();

    public void adicionaTelefone(Telefone telefone, int sequencia) {
        telefone.setPessoa(this);
        telefone.setTelefoneId(sequencia, telefone.getPessoa());
        telefones.add(telefone);
    }
}
