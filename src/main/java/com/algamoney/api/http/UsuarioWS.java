package com.algamoney.api.http;


import com.algamoney.api.http.domain.response.ConsultaPermissoesUsuarioResponse;
import com.algamoney.api.usecase.user.ConsultaPermissoesUsuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/users")
@Api(tags = "User")
@AllArgsConstructor
public class UsuarioWS {
    private final ConsultaPermissoesUsuario consultaPermissoesUsuario;

    @ApiOperation(value = "Get user's permissions")
    @GetMapping(path = "/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaPermissoesUsuarioResponse consultaPermissoes(@ApiParam("id") @PathVariable("codigo") Long codigo) {
       return new ConsultaPermissoesUsuarioResponse(consultaPermissoesUsuario.executar(codigo));
    }
}
