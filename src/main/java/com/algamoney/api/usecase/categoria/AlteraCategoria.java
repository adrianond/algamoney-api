package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.request.CategoriaRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import static com.algamoney.api.commons.Constants.CACHE_CATEGORIA;

@Component
@AllArgsConstructor
@Transactional
public class AlteraCategoria {
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;

    @CacheEvict(value = CACHE_CATEGORIA, allEntries = true)
    public void executar(Long id, CategoriaRequest request) {
        Categoria categoria = categoriaRepositoryFacade.findById(id);
        categoria.setNome(request.getCategoriaDTO().getNome());
    }
}
