package com.algamoney.api.http;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.http.domain.request.LancamentoFilter;
import com.algamoney.api.http.domain.request.LancamentoRequest;
import com.algamoney.api.http.domain.response.LancamentoResponse;
import com.algamoney.api.http.domain.response.LancamentosPageResponse;
import com.algamoney.api.http.domain.response.LancamentosResponse;
import com.algamoney.api.http.domain.response.ResumoLancamentosPageResponse;
import com.algamoney.api.usecase.lancamento.ConsultarLancamento;
import com.algamoney.api.usecase.lancamento.ConsultarLancamentos;
import com.algamoney.api.usecase.lancamento.ExcluirLancamento;
import com.algamoney.api.usecase.lancamento.PersistirLancamento;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/entries")
@Api(tags = "Entries")
@AllArgsConstructor
public class LancamentoWS {
    private final PersistirLancamento persistirLancamento;
    private final ExcluirLancamento excluirLancamento;
    private final ConsultarLancamento consultarLancamento;
    private final ConsultarLancamentos consultarLancamentos;

    @ApiOperation(value = "Save new Entry")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoResponse salvarLancamento(@Valid @RequestBody LancamentoRequest request
                                              /* @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                               @RequestHeader(value = "Authorization") String authorization*/) {
        return new LancamentoResponse(persistirLancamento.executar(request));
    }

    @ApiOperation(value = "Update entry")
    @PutMapping(path = "/entry/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void alterarLancamento(@ApiParam @PathVariable("id") Long id, @RequestBody LancamentoRequest request) {
        persistirLancamento.executar(id, request);
    }

    @ApiOperation(value = "Delete a Entry")
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    @DeleteMapping(path = "/entry/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirLancamento(@ApiParam @PathVariable("id") Long id
                                  /*@ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                  @RequestHeader(value = "Authorization") String authorization*/) {
        excluirLancamento.executar(id);

    }

    @ApiOperation(value = "Get a Entry")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping(path = "/entry/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LancamentoResponse consultarLancamento(@ApiParam @PathVariable("id") Long id,
                                                  @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                                  @RequestHeader(value = "Authorization") String authorization) {
        return new LancamentoResponse(consultarLancamento.executar(id));
    }

    @ApiOperation(value = "Get Entries")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public LancamentosResponse consultarLancamentos(/*@ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                                    @RequestHeader(value = "Authorization") String authorization*/) {
        return new LancamentosResponse(consultarLancamentos.executar());
    }

    @ApiOperation(value = "Get Entries paginated v1")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping(path = "/paginated/v1")
    @ResponseStatus(HttpStatus.OK)
    public LancamentosPageResponse pesquisar(Pageable pageable,
                                             @RequestParam(value = "dataVencimentoDe", required = false)  @DateTimeFormat(iso = ISO.DATE) LocalDate dataVencimentoDe,
                                             @RequestParam(value = "dataVencimentoAte", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dataVencimentoAte,
                                             @RequestParam(value = "tipoLancamento", required = false) TipoLancamento tipoLancamento,
                                             @RequestParam(value = "descricao", required = false) String descricao
                                             /*@ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                                 @RequestHeader(value = "Authorization") String authorization*/) {
        return new LancamentosPageResponse(consultarLancamentos.executarPaginacaoQueryDsl(pageable, dataVencimentoDe, dataVencimentoAte, tipoLancamento, descricao));
    }

    @ApiOperation(value = "Get Entries paginated v2")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping(path = "/paginated/v2")
    public LancamentosPageResponse pesquisar(LancamentoFilter lancamentoFilter, Pageable pageable,
                                             @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                             @RequestHeader(value = "Authorization") String authorization) {
        return new LancamentosPageResponse(consultarLancamentos.executarPaginacao(lancamentoFilter, pageable));
    }

    @GetMapping(path = "/paginated/v2", params = "resumo")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    public ResumoLancamentosPageResponse resumir(LancamentoFilter lancamentoFilter, Pageable pageable,
                                                 @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                                 @RequestHeader(value = "Authorization") String authorization) {
        return new ResumoLancamentosPageResponse(consultarLancamentos.resumir(lancamentoFilter, pageable));
    }
}
