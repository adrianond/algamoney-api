package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.exception.CategoriaNotFoundException;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.time.Duration;
import java.util.List;

@Component
@Slf4j
public class ConsultaCategoriasV2 {
    private final CategoriaRepositoryFacade categoriaRepositoryFacade;
    private final ObjectMapper objectMapper;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${algamoney.categoria.v2.empresa:330}")
    private Integer empresa;

    @Value("${algamoney.categoria.v2.operacao:5}")
    private Integer operacao;

    @Value("${algamoney.categoria.v2.lojista:10}")
    private Integer lojista;

    @Value("${algamoney.categoria.v2.proposta:1010}")
    private Long proposta;

    public ConsultaCategoriasV2(CategoriaRepositoryFacade categoriaRepositoryFacade,
                                 ObjectMapper objectMapper,
                                 RedisTemplate<String, String> redisTemplate) {
        this.categoriaRepositoryFacade = categoriaRepositoryFacade;
        this.objectMapper = objectMapper;
        this.redisTemplate = redisTemplate;
    }

    public List<CategoriaDTO> executar() {
        log.info("Consultar categorias");
        try {
            String chave = "proposta:" + proposta;

            String categoriasCached = redisTemplate.opsForValue().get(chave);
            if (categoriasCached != null)
                return objectMapper.readValue(categoriasCached, new TypeReference<List<CategoriaDTO>>() {});

            String json = categoriaRepositoryFacade.findAllV2(empresa, operacao, lojista, "N", null, proposta);
            redisTemplate.opsForValue().set(chave, json, Duration.ofMinutes(10));
            return objectMapper.readValue(json, new TypeReference<List<CategoriaDTO>>() {});
        } catch (SQLException e) {
            log.error("Erro ao executar a consulta de categorias", e);
            throw new CategoriaNotFoundException("Erro ao executar a consulta de categorias");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
