package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.algamoney.api.http.domain.request.AdicionaCategoriaRequest;
import com.algamoney.api.http.domain.request.CategoriaRequest;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;

import static com.algamoney.api.commons.Constants.CACHE_CATEGORIA;

@Component
@AllArgsConstructor
public class AdicionaCategoria {
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;

    @CacheEvict(value = CACHE_CATEGORIA, allEntries = true)
    public CategoriaDTO executar(CategoriaRequest request) {
       Categoria categoria = categoriaRepositoryFacade.save(new Categoria(request.getCategoriaDTO().getNome()));
        return CategoriaDTO.builder()
                .id(categoria.getId())
                .nome(categoria.getNome())
                .build();
    }
}
