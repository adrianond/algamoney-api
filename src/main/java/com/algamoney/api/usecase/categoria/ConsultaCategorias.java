package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.algamoney.api.http.domain.response.ConsultaCategoriasResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConsultaCategorias {
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;

    public List<CategoriaDTO> executar() {
        return categoriaRepositoryFacade.findAll().stream()
                .map(categoria -> CategoriaDTO.builder()
                        .id(categoria.getId())
                        .nome(categoria.getNome())
                        .build())
                        .collect(Collectors.toList());
    }
}
