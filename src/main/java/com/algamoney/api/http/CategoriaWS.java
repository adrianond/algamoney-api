package com.algamoney.api.http;

import com.algamoney.api.database.entity.Categoria;
import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.http.domain.request.AdicionaCategoriaRequest;
import com.algamoney.api.http.domain.request.CategoriaRequest;
import com.algamoney.api.http.domain.response.AdicionaCategoriaResponse;
import com.algamoney.api.http.domain.response.ConsultaCategoriaResponse;
import com.algamoney.api.http.domain.response.ConsultaCategoriasResponse;
import com.algamoney.api.usecase.categoria.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private final ExcluiCategoria excluiCategoria;

    private final AlteraCategoria alteraCategoria;
    private final ApplicationEventPublisher publisher;

    @ApiOperation(value = "Get category")
    @GetMapping(path = "/category/{id}")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaCategoriaResponse consultaCategoria(@ApiParam("id") @PathVariable("id") Long id,
                                                       @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                                       @RequestHeader(value = "Authorization") String authorization) {
       return new ConsultaCategoriaResponse(consultaCategoria.executar(id));
    }

    @ApiOperation(value = "Get categories")
    @GetMapping()
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_CATEGORIA') and #oauth2.hasScope('read')")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaCategoriasResponse consultaCategorias(@ApiParam(required = true, value = "Authorization: Bearer <TOKEN>") @RequestHeader(value = "Authorization") String authorization) {
       return new ConsultaCategoriasResponse(consultaCategorias.executar());
    }


    @ApiOperation(value = "Add new category")
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.CREATED)
    public AdicionaCategoriaResponse adicionaCategoria(@Valid @RequestBody CategoriaRequest request,
                                  @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                  @RequestHeader(value = "Authorization") String authorization) {
        return new AdicionaCategoriaResponse(adicionaCategoria.executar(request));

    }

    @ApiOperation(value = "Update category")
    @PutMapping(path = "/category/{id}")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_CATEGORIA') and #oauth2.hasScope('write')")
    @ResponseStatus(HttpStatus.OK)
    public void alterarCategoria(@ApiParam @PathVariable("id") Long id,
                                 @Valid @RequestBody CategoriaRequest request,
                                  @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                  @RequestHeader(value = "Authorization") String authorization) {
        alteraCategoria.executar(id, request);
    }

    @ApiOperation(value = "Delete a category")
    @PreAuthorize("hasAuthority('ROLE_REMOVER_CATEGORIA') and #oauth2.hasScope('write')")
    @DeleteMapping(path = "/category/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirCategoria(@ApiParam @PathVariable("id") Long id,
                                  @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                  @RequestHeader(value = "Authorization") String authorization) {
        excluiCategoria.executar(id);
    }
}
