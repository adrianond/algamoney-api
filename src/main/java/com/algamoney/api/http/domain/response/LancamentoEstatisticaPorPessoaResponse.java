package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.LancamentoEstatisticaPorPessoaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
public class LancamentoEstatisticaPorPessoaResponse {
    private Page<LancamentoEstatisticaPorPessoaDTO> page;
}
