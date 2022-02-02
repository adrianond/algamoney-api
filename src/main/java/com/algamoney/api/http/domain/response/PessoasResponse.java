package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.PessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;


@Getter
@AllArgsConstructor
public class PessoasResponse {
    private List<PessoaDTO> pessoas;
}
