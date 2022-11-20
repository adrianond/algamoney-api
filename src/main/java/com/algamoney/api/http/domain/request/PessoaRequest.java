package com.algamoney.api.http.domain.request;

import com.algamoney.api.http.domain.PessoaDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PessoaRequest {
    @NotNull
    @Valid
    private PessoaDTO pessoaDTO;
}
