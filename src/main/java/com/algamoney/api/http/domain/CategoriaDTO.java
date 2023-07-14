package com.algamoney.api.http.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoriaDTO implements Serializable {
    private Long id;

    @NotNull
    @NotBlank
    @ApiModelProperty(
       value = "Descrição da categoria",
       required = true,
       dataType = "String",
       example =  "Lazer"
    )
    private String nome;
}
