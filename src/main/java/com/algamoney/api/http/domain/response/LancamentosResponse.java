package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.LancamentoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LancamentosResponse {
    private final List<LancamentoDTO> lancamentos;
}
