/*
package com.algamoney.api.http;

import com.algamoney.api.event.RecursoCriadoEvent;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.request.PessoaRequest;
import com.algamoney.api.http.domain.response.PessoaResponse;
import com.algamoney.api.usecase.pessoa.AdicionaPessoa;
import com.algamoney.api.usecase.pessoa.ConsultaPessoa;
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
@RequestMapping(path = "/api/person/phones")
@Api(tags = "Phone")
@AllArgsConstructor
public class TelefoneWS {
    private final AdicionaPessoa adicionaPessoa;
    private final ApplicationEventPublisher publisher;

    @ApiOperation("Save phone number")
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public PessoaResponse savePhoneNumber(@Valid @RequestBody PessoaRequest pessoaRequest, HttpServletResponse response) {
        PessoaDTO pessoaSalva = adicionaPessoa.executar(pessoaRequest);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, pessoaSalva.getId()));
        return new PessoaResponse(pessoaSalva);
    }
}
*/
