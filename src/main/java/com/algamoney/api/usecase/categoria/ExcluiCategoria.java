package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import static com.algamoney.api.commons.Constants.CACHE_CATEGORIA;

@Component
@AllArgsConstructor
public class ExcluiCategoria {
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;

    @CacheEvict(value = CACHE_CATEGORIA, allEntries=true)
    public void executar(Long id) {
        Categoria categoria = categoriaRepositoryFacade.findById(id);
        categoriaRepositoryFacade.delete(categoria);
    }
}
