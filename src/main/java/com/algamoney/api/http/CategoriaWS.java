package com.algamoney.api.http;


import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.http.domain.request.CategoriaRequest;
import com.algamoney.api.http.domain.response.ConsultaCategoriaResponse;
import com.algamoney.api.http.domain.response.ConsultaCategoriasResponse;
import com.algamoney.api.usecase.categoria.AdicionaCategoria;
import com.algamoney.api.usecase.categoria.ConsultaCategoria;
import com.algamoney.api.usecase.categoria.ConsultaCategorias;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/categories")
@Api(tags = "Category")
@AllArgsConstructor
public class CategoriaWS {
    private final ConsultaCategoria consultaCategoria;
    private final ConsultaCategorias consultaCategorias;
    private final AdicionaCategoria adicionaCategoria;
    private final ApplicationEventPublisher publisher;
    
    @ApiOperation(value = "Get category")
    @GetMapping(path = "/category/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaCategoriaResponse consultaCategoria(@ApiParam("id") @PathVariable("id") Long id) {
       return new ConsultaCategoriaResponse(consultaCategoria.executar(id));
    }

    @ApiOperation(value = "Get categories")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ConsultaCategoriasResponse consultaCategorias() {
       return new ConsultaCategoriasResponse(consultaCategorias.executar());
    }

    @ApiOperation(value = "Add new category")
    @PostMapping(path = "/category")
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionaCategoria(@Valid @RequestBody CategoriaRequest request, HttpServletResponse response) {
        Categoria categoria = adicionaCategoria.executar(request);
        /**
         * para retornar no header da requisição o location do recurso criado
         */
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoria.getId()));
    }
}
