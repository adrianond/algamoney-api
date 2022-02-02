package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.PessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;


@Getter
@AllArgsConstructor
public class PessoasPageResponse {
    private Page<PessoaDTO> pessoas;
}
