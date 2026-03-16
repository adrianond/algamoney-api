package com.algamoney.api.usecase.categoria;

import com.algamoney.api.database.repository.CategoriaRepositoryFacade;
import com.algamoney.api.http.domain.CategoriaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ConsultaCategoriasV2Test {

    @Mock
    private CategoriaRepositoryFacade categoriaRepositoryFacade;

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    // ObjectMapper real: apenas serializa/desserializa JSON, sem efeitos colaterais
    private final ObjectMapper objectMapper = new ObjectMapper();

    private ConsultaCategoriasV2 consultaCategoriasV2;

    @Before
    public void setUp() {
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        consultaCategoriasV2 = new ConsultaCategoriasV2(categoriaRepositoryFacade, objectMapper, redisTemplate);

        // simula a injeção dos @Value (não processados fora do contexto Spring)
        ReflectionTestUtils.setField(consultaCategoriasV2, "empresa", 330);
        ReflectionTestUtils.setField(consultaCategoriasV2, "operacao", 5);
        ReflectionTestUtils.setField(consultaCategoriasV2, "lojista", 10);
        ReflectionTestUtils.setField(consultaCategoriasV2, "proposta", 1010L);
    }

    @Test
    public void deveRetornarCategoriasDoCache() throws Exception {
        String jsonCached = "[{\"id\":1,\"nome\":\"Alimentação\"},{\"id\":2,\"nome\":\"Transporte\"}]";
        when(valueOperations.get(anyString())).thenReturn(jsonCached);

        List<CategoriaDTO> resultado = consultaCategoriasV2.executar();

        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Alimentação", resultado.get(0).getNome());
    }

    @Test
    public void deveBuscarNoRepositorioQuandoCacheVazio() throws Exception {
        String jsonDoBanco = "[{\"id\":1,\"nome\":\"Saúde\"}]";
        when(valueOperations.get(anyString())).thenReturn(null);
        when(categoriaRepositoryFacade.findAllV2(any(Integer.class), any(Integer.class),
                any(Integer.class), anyString(), any(), anyLong())).thenReturn(jsonDoBanco);

        List<CategoriaDTO> resultado = consultaCategoriasV2.executar();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Saúde", resultado.get(0).getNome());
        verify(valueOperations).set(anyString(), anyString(), any());
    }
}
