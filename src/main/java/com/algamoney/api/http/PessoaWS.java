package com.algamoney.api.http;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.request.AtualizarPessoaRequest;
import com.algamoney.api.http.domain.request.PessoaRequest;
import com.algamoney.api.http.domain.request.TelefoneRequest;
import com.algamoney.api.http.domain.response.PessoaResponse;
import com.algamoney.api.usecase.pessoa.AdicionaPessoa;
import com.algamoney.api.usecase.pessoa.AtualizarPessoa;
import com.algamoney.api.usecase.pessoa.ConsultaPessoa;
import com.algamoney.api.usecase.pessoa.ExcluirPessoa;
import com.algamoney.api.usecase.telefone.SalvarTelefoneAssincrono;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/persons")
@Api(tags = "Person")
@AllArgsConstructor
public class PessoaWS {
    private final AdicionaPessoa adicionaPessoa;
    private final ConsultaPessoa consultaPessoa;
    private final SalvarTelefoneAssincrono salvarTelefoneAssincrono;
    private final ExcluirPessoa excluirPessoa;
    private final AtualizarPessoa atualizarPessoa;
    private final ApplicationEventPublisher publisher;

    @ApiOperation("Save a new Person")
    @PostMapping(path = "/person")
    @ResponseStatus(HttpStatus.CREATED)
    public PessoaResponse add(@Valid @RequestBody PessoaRequest pessoaRequest, HttpServletResponse response) {
        PessoaDTO pessoaSalva = adicionaPessoa.executar(pessoaRequest);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        return new PessoaResponse(pessoaSalva);
    }

    @ApiOperation(value = "Get persom by id")
    @GetMapping(path = "/person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse consultaPessoa(@ApiParam("id") @PathVariable("id") Long id) {
        return new PessoaResponse(consultaPessoa.executar(id));
    }

    @ApiOperation(value = "Save person phone number")
    @PostMapping(path = "person/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void salvarTelefone(@ApiParam("id") @PathVariable("id") Long id,
                               @Valid @RequestBody List<TelefoneRequest> telefones) {
        salvarTelefoneAssincrono.executar(id, telefones);
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
                                             @Valid @RequestBody AtualizarPessoaRequest request) {
        return new PessoaResponse(atualizarPessoa.executar(id, request));
    }

    @ApiOperation(value = "Update propertie person active")
    @PutMapping(path = "/person/{id}/ativo")
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse alterarPropriedadeAtivo(@ApiParam @PathVariable("id") Long id,
                                                  @RequestBody Boolean ativo) {
        return new PessoaResponse(atualizarPessoa.executar(id, ativo));
    }
}
