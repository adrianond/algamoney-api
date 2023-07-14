package com.algamoney.api.http.domain.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AdicionaCategoriaRequest {
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
