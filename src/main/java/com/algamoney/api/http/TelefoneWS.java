package com.algamoney.api.http;

import com.algamoney.api.http.domain.request.TelefoneRequest;
import com.algamoney.api.usecase.telefone.SalvaTelefoneAssincrono;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "/api/person")
@Api(tags = "Phone")
@AllArgsConstructor
public class TelefoneWS {
    private final SalvaTelefoneAssincrono salvaTelefoneAssincrono;

    @ApiOperation(value = "Save person phone number")
    @PostMapping(path = "/{id}/phones")
    @ResponseStatus(HttpStatus.OK)
    public void salvarTelefone(@ApiParam("id") @PathVariable("id") Long id,
                               @Valid @RequestBody TelefoneRequest request,
                               @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                               @RequestHeader(value = "Authorization") String authorization) {
        salvaTelefoneAssincrono.executar(id, request.getTelefoneDTOList());
    }
}

