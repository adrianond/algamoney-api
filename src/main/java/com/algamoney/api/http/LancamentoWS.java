package com.algamoney.api.http;

import com.algamoney.api.http.domain.request.LancamentoRequest;
import com.algamoney.api.http.domain.response.LancamentoResponse;
import com.algamoney.api.http.domain.response.LancamentosResponse;
import com.algamoney.api.usecase.lancamento.ConsultarLancamento;
import com.algamoney.api.usecase.lancamento.ConsultarLancamentos;
import com.algamoney.api.usecase.lancamento.ExcluirLancamento;
import com.algamoney.api.usecase.lancamento.SalvarLancamento;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/lancamentos")
@Api(tags = "Entries")
@AllArgsConstructor
public class LancamentoWS {
    private final SalvarLancamento salvarLancamento;
    private final ExcluirLancamento excluirLancamento;
    private final ConsultarLancamento consultarLancamento;
    private final ConsultarLancamentos consultarLancamentos;

    @ApiOperation(value = "Save new Entry")
    @PostMapping(path = "/lancamento")
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoResponse salvarLancamento(@Valid @RequestBody LancamentoRequest request) {
        return new LancamentoResponse(salvarLancamento.executar(request));
    }

    @ApiOperation(value = "Delete a Entry")
    @DeleteMapping(path = "/lancamento/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirLancamento(@ApiParam @PathVariable("id") Long id) {
        excluirLancamento.executar(id);

    }

    @ApiOperation(value = "Get a Entry")
    @GetMapping(path = "/lancamento/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LancamentoResponse consultarLancamento(@ApiParam @PathVariable("id") Long id) {
        return new LancamentoResponse(consultarLancamento.executar(id));
    }

    @ApiOperation(value = "Get Entries")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public LancamentosResponse consultarLancamentos() {
        return new LancamentosResponse(consultarLancamentos.executar());
    }
}
