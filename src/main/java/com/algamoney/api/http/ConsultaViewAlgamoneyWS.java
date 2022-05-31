package com.algamoney.api.http;

import com.algamoney.api.http.domain.request.AlgamoneyFilterRequest;
import com.algamoney.api.http.domain.response.ConsultaViewAlgamoneyPageResponse;
import com.algamoney.api.http.domain.response.ConsultaViewAlgamoneyResponse;
import com.algamoney.api.usecase.ConsultaViewAlgamoney;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/algamoney/view")
@Api(tags = "Algamoney View")
@AllArgsConstructor
public class ConsultaViewAlgamoneyWS {
    private final ConsultaViewAlgamoney consultaViewAlgamoney;

    @ApiOperation(value = "Get view")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ConsultaViewAlgamoneyResponse consultaView() {
       return new ConsultaViewAlgamoneyResponse(consultaViewAlgamoney.executar());
    }
    
    @ApiOperation(value = "Get view paginated v1")
    @GetMapping(path = "/paginated/v1")
    @ResponseStatus(HttpStatus.OK)
    public ConsultaViewAlgamoneyPageResponse consultaViewV1(Pageable pageable,
                                                                      @RequestParam(value = "nome", required = false) String nome,
                                                                      @RequestParam(value = "logradouro", required = false) String logradouro,
                                                                      @RequestParam(value = "descricao", required = false) String descricao,
                                                                      @RequestParam(value = "bairro", required = false) String  bairro,
                                                                      @RequestParam(value = "tipoLancamento", required = false) String tipoLancamento,
                                                                      @RequestParam(value = "nomeCategoria", required = false) String  nomeCategoria) {

       return new ConsultaViewAlgamoneyPageResponse(consultaViewAlgamoney.executar(pageable, nome, logradouro, descricao, bairro, tipoLancamento, nomeCategoria));
    }

    @ApiOperation(value = "Get view paginated v2")
    @GetMapping("/paginated/v2")
    public ConsultaViewAlgamoneyPageResponse consultaViewV2(AlgamoneyFilterRequest filters, Pageable page) {
        return consultaViewAlgamoney.executar(filters, page);
    }
}
