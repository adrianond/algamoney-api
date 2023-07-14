package com.algamoney.api.usecase.categoria;

import com.algamoney.api.commons.Constants;
import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.algamoney.api.commons.Constants.CACHE_CATEGORIA;
import static com.algamoney.api.commons.Constants.CACHE_CATEGORIA_KEY;


@Component
@AllArgsConstructor
@Slf4j
public class ConsultaCategorias {
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;

    @Cacheable(value = CACHE_CATEGORIA, key = CACHE_CATEGORIA_KEY)
    public List<CategoriaDTO> executar() {
        log.info("Consultar categorias");
        return categoriaRepositoryFacade.findAll().stream()
                .map(categoria -> CategoriaDTO.builder()
                        .id(categoria.getId())
                        .nome(categoria.getNome())
                        .build())
                        .collect(Collectors.toList());
    }

    @CacheEvict(value = CACHE_CATEGORIA, allEntries = true)
    @Scheduled(fixedRate = Constants.MINUTO)
    public void clearCache() {
        log.info("Limpando cache de consulta de categorias!");
    }
}
