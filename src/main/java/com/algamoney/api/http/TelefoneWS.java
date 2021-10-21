package com.algamoney.api.http;

import com.algamoney.api.http.domain.request.TelefoneRequest;
import com.algamoney.api.usecase.telefone.SalvarTelefoneAssincrono;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/person")
@Api(tags = "Phone")
@AllArgsConstructor
public class TelefoneWS {
    private final SalvarTelefoneAssincrono salvarTelefoneAssincrono;

    @ApiOperation(value = "Save person phone number")
    @PostMapping(path = "/{id}/phone")
    @ResponseStatus(HttpStatus.OK)
    public void salvarTelefone(@ApiParam("id") @PathVariable("id") Long id,
                               @Valid @RequestBody List<TelefoneRequest> telefones) {
        salvarTelefoneAssincrono.executar(id, telefones);
    }
}

