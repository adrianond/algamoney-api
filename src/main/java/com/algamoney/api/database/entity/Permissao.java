package com.algamoney.api.database.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "permissao")
@NoArgsConstructor
@Data
public class Permissao implements Serializable  {

    @Id
    private Long codigo;
    private String descricao;
}
