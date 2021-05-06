package com.algamoney.api.database.entity;

import com.algamoney.api.database.entity.embedded.TelefoneId;
import com.algamoney.api.database.entity.enumeration.CategoriaTelefone;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Data
public class Telefone implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private TelefoneId id;

    @MapsId("id_pessoa")
    @ManyToOne(targetEntity = Pessoa.class, optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_pessoa", referencedColumnName = "id", nullable = false)
    private Pessoa pessoa;

    @Column(nullable = false)
    private String numero;

    private String ramal;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CategoriaTelefone categoria;

    public int getSequencia() {
        return (id == null) ? 0 : id.getSequencia();
    }

    public void setTelefoneId(int sequencia, Pessoa pessoa) {
        if (id == null)
            id = new TelefoneId();
        id.setSequencia(sequencia);
        id.setPessoa(pessoa.getId());
    }
}
