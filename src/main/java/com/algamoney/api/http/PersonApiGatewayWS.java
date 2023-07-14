package com.algamoney.api.http;

import com.algamoney.api.http.domain.request.PersonRequest;
import com.algamoney.api.http.domain.response.PersonResponse;
import com.algamoney.api.usecase.person.SalvaPessoa;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/gateway/persons")
@Api(tags = "ApiGateway")
@AllArgsConstructor
public class PersonApiGatewayWS {
    private final SalvaPessoa salvaPessoa;

    @ApiOperation(value = "Save Person")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse salvarPessoa(@Valid @RequestBody PersonRequest personRequest,
                                       @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                       @RequestHeader(value = "Authorization") String authorization) {
        return salvaPessoa.executar(personRequest);
    }
}
