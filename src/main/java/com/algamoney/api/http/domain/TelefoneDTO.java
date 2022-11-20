package com.algamoney.api.http.domain;

import com.algamoney.api.database.entity.enumeration.CategoriaTelefone;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.algamoney.api.commons.ValidationConstants.APENAS_DIGITOS;

@Setter
@Getter
public class TelefoneDTO {

    @ApiModelProperty(
       value = "Identificador unico do telefone",
       hidden = true
    )
    private TelefoneIdDTO id;

    @NotNull
    @NotBlank
    @Size(min = 11, message = "Telefone deve conter 11 digitos")
    @Pattern(regexp = APENAS_DIGITOS, message="Campo Telefone deve conter apenas números")
    @ApiModelProperty(
            value = "Celular do Cliente prencher ddd + numero",
            required = true,
            dataType = "String",
            example = "11999999999"
    )
    private String numero;

    @ApiModelProperty(
            value = "Ramal do telefone",
            dataType = "String",
            example = "297"
    )
    private String ramal;

    @NotNull
    @NotBlank
    @ApiModelProperty(
         value = "Categoria do telefone",
         required = true,
         dataType = "String",
         example = "RESIDENCIAL"

    )
    private CategoriaTelefone categoriaTelefone;

    @ApiModelProperty(
            value = "Identificador sequencial do telefone",
            hidden = true
    )
    private int sequencia;
}
