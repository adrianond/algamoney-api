package com.algamoney.api.http;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.request.PessoaRequest;
import com.algamoney.api.http.domain.response.PessoaResponse;
import com.algamoney.api.http.domain.response.PessoasPageResponse;
import com.algamoney.api.http.domain.response.PessoasResponse;
import com.algamoney.api.usecase.pessoa.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/persons")
@Api(tags = "Person")
@AllArgsConstructor
public class PessoaWS {
    private final PersistiPessoa persistiPessoa;
    private final ConsultaPessoa consultaPessoa;
    private final ExcluiPessoa excluiPessoa;
    private final AtualizaStatusPessoa atualizaStatusPessoa;
    private final ConsultaPessoas consultaPessoas;
    private final ApplicationEventPublisher publisher;

    @ApiOperation("Save a new Person")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponse add(@Valid @RequestBody PessoaRequest pessoaRequest, HttpServletResponse response,
                             @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>") @RequestHeader(value = "Authorization") String authorization) {
        PessoaDTO pessoaSalva = persistiPessoa.executar(pessoaRequest);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        return new PessoaResponse(pessoaSalva);
    }

    @ApiOperation(value = "Get person by id")
    @GetMapping(path = "/person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse consultaPessoa(@ApiParam("id") @PathVariable("id") Long id,
                                         @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                         @RequestHeader(value = "Authorization") String authorization) {
        return new PessoaResponse(consultaPessoa.executar(id));
    }

    @ApiOperation(value = "Get persons ")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public PessoasResponse consultaPessoas(@ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                         @RequestHeader(value = "Authorization") String authorization) {
        return new PessoasResponse(consultaPessoas.executar());
    }

    @ApiOperation(value = "Get persons paginated ")
    @GetMapping(path = "/paginated")
    @ResponseStatus(HttpStatus.OK)
    public PessoasPageResponse consultaPessoasComPaginacao(Pageable pageable,
                                         @RequestParam(value = "nome", required = false) String nome,
                                         @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                         @RequestHeader(value = "Authorization") String authorization) {
        return new PessoasPageResponse(consultaPessoas.executarComPaginacao(pageable, nome));
    }


    @ApiOperation(value = "Delete person by id")
    @DeleteMapping(path = "/person/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPessoa(@ApiParam @PathVariable("id") Long id,
                              @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                              @RequestHeader(value = "Authorization") String authorization) {
        excluiPessoa.executar(id);
    }

    @ApiOperation(value = "Update person by id")
    @PutMapping(path = "/person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse alterarDadosPessoa(@ApiParam @PathVariable("id") Long id,
                                             @Valid @RequestBody PessoaRequest request,
                                             @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                             @RequestHeader(value = "Authorization") String authorization) {
        return new PessoaResponse(persistiPessoa.executar(id, request));
    }

    @ApiOperation(value = "Update propertie person active")
    @PutMapping(path = "/person/{id}/ativo")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse alterarPropriedadeAtivo(@ApiParam @PathVariable("id") Long id,
                                                  @RequestBody Boolean ativo,
                                                  @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                                  @RequestHeader(value = "Authorization") String authorization) {
        return new PessoaResponse(atualizaStatusPessoa.executar(id, ativo));
    }
}
