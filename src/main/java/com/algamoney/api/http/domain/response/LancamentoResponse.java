package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.LancamentoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LancamentoResponse {
    private final LancamentoDTO lancamentoDTO;
}
