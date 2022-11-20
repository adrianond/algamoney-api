package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.LancamentoEstatisticaPorDiaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
@AllArgsConstructor
public class LancamentoEstatisticaPorDiaResponse {
    private Page<LancamentoEstatisticaPorDiaDTO> page;
}
