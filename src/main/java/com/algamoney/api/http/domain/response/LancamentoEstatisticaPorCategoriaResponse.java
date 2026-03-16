package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.LancamentoEstatisticaPorCategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LancamentoEstatisticaPorCategoriaResponse {
    private List<LancamentoEstatisticaPorCategoriaDTO> lancamentosDTO;
}
