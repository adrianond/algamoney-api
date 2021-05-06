package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.request.CategoriaRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AdicionaCategoria {
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;

    public Categoria executar(CategoriaRequest request) {
       return categoriaRepositoryFacade.save(new Categoria(request.getNome()));
    }
}
