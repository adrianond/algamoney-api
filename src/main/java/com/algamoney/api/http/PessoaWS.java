package com.algamoney.api.http;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.request.PessoaRequest;
import com.algamoney.api.http.domain.response.PessoaResponse;
import com.algamoney.api.usecase.pessoa.AtualizarPessoa;
import com.algamoney.api.usecase.pessoa.CadastrarPessoa;
import com.algamoney.api.usecase.pessoa.ConsultaPessoa;
import com.algamoney.api.usecase.pessoa.ExcluirPessoa;
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
@RequestMapping(path = "/api/persons")
@Api(tags = "Person")
@AllArgsConstructor
public class PessoaWS {
    private final CadastrarPessoa cadastrarPessoa;
    private final ConsultaPessoa consultaPessoa;
    private final ExcluirPessoa excluirPessoa;
    private final AtualizarPessoa atualizarPessoa;
    private final ApplicationEventPublisher publisher;

    @ApiOperation("Save a new Person")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponse add(@Valid @RequestBody PessoaRequest pessoaRequest, HttpServletResponse response,
                              @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>") @RequestHeader(value = "Authorization") String authorization) {
        PessoaDTO pessoaSalva = cadastrarPessoa.salvar(pessoaRequest);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        return new PessoaResponse(pessoaSalva);
    }

    @ApiOperation(value = "Get persom by id")
    @GetMapping(path = "/person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse consultaPessoa(@ApiParam("id") @PathVariable("id") Long id) {
        return new PessoaResponse(consultaPessoa.executar(id));
    }


    @ApiOperation(value = "Delete person by id")
    @DeleteMapping(path = "/person/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirPessoa(@ApiParam @PathVariable("id") Long id) {
        excluirPessoa.executar(id);
    }

    @ApiOperation(value = "Update person by id")
    @PutMapping(path = "/person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse alterarDadosPessoa(@ApiParam @PathVariable("id") Long id,
                                             @Valid @RequestBody PessoaRequest request,
                                             @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>") @RequestHeader(value = "Authorization") String authorization) {
        return new PessoaResponse(cadastrarPessoa.atualizar(id, request));
    }

    @ApiOperation(value = "Update propertie person active")
    @PutMapping(path = "/person/{id}/ativo")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse alterarPropriedadeAtivo(@ApiParam @PathVariable("id") Long id,
                                                  @RequestBody Boolean ativo) {
        return new PessoaResponse(atualizarPessoa.executar(id, ativo));
    }
}
