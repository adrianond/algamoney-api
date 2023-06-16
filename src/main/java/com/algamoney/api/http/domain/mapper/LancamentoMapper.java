package com.algamoney.api.http.domain.mapper;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.http.domain.LancamentoDTO;
import org.mapstruct.Mapper;
@Mapper
public interface LancamentoMapper {
    LancamentoDTO lancamentoEntityToLancamentoDto(Lancamento lancamento);
}
