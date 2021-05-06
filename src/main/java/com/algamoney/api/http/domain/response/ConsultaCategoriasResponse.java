package com.algamoney.api.http.domain.response;

import com.algamoney.api.http.domain.CategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaCategoriasResponse {
    private List<CategoriaDTO> categorias = new ArrayList<>();
}
