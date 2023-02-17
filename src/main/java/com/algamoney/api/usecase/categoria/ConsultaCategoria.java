package com.algamoney.api.usecase.categoria;

import com.algamoney.api.commons.Constants;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ConsultaCategoria {
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;
    private static final String CACHE_NAME = "algamoney.cache";

    @Cacheable(CACHE_NAME)
    public List<CategoriaDTO> executar() {
        return categoriaRepositoryFacade.findAll().stream()
                .map(categoria -> CategoriaDTO.builder()
                        .id(categoria.getId())
                        .nome(categoria.getNome())
                        .build())
                .collect(Collectors.toList());
    }

    @CacheEvict(value = CACHE_NAME, allEntries = true)
    @Scheduled(fixedRate = Constants.HORA)
    public void clearCache() {
    }
}
