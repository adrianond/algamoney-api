package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsultaCategoria {
    private CategoriaRepositoryFacade categoriaRepositoryFacade;

    public CategoriaDTO executar(Long id) {
       Categoria categoria = categoriaRepositoryFacade.findById(id);
       return CategoriaDTO.builder()
                    .id(categoria.getId())
                    .nome(categoria.getNome())
               .build();
    }
}
