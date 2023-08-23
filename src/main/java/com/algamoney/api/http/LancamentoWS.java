package com.algamoney.api.http;

import com.algamoney.api.database.entity.enumeration.TipoLancamento;
import com.algamoney.api.http.domain.request.LancamentoFilter;
import com.algamoney.api.http.domain.request.LancamentoRequest;
import com.algamoney.api.http.domain.response.*;
//import com.algamoney.api.usecase.cloud.EnviarArquivoS3;
import com.algamoney.api.usecase.lancamento.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.LocalDate;

@RestController
@RequestMapping(path = "/api/entries")
@Api(tags = "Entries")
@AllArgsConstructor
public class LancamentoWS {
    private final PersistiLancamento persistiLancamento;
    private final ExcluiLancamento excluiLancamento;
    private final ConsultaLancamento consultaLancamento;
    private final ConsultaLancamentos consultarLancamentos;
    private final ConsultaLancamentosPorPessoa consultaLancamentosPorPessoa;
    private final ConsultaLancamentosPorDia consultaLancamentosPorDia;
    private final ConsultaLancamentosPorCategoria consultaLancamentosPorCategoria;
    //private final EnviarArquivoS3 enviarArquivoS3;

    @ApiOperation(value = "Save new Entry")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public LancamentoResponse salvarLancamento(@Valid @RequestBody LancamentoRequest request,
                                               @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                               @RequestHeader(value = "Authorization") String authorization) {
        return new LancamentoResponse(persistiLancamento.executar(request));
    }

    @ApiOperation(value = "Update entry")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and #oauth2.hasScope('write')")
    @PutMapping(path = "/entry/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void alterarLancamento(@ApiParam @PathVariable("id") Long id,
                                  @RequestBody LancamentoRequest request,
                                  @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                  @RequestHeader(value = "Authorization") String authorization) {
        //persistirLancamento.executar(id, request);
    }

    @ApiOperation(value = "Delete a Entry")
    @PreAuthorize("hasAuthority('ROLE_REMOVER_LANCAMENTO') and #oauth2.hasScope('write')")
    @DeleteMapping(path = "/entry/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirLancamento(@ApiParam @PathVariable("id") Long id,
                                  @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                  @RequestHeader(value = "Authorization") String authorization) {
        excluiLancamento.executar(id);
    }

    @ApiOperation(value = "Get a Entry")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping(path = "/entry/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LancamentoResponse consultarLancamento(@ApiParam @PathVariable("id") Long id,
                                                  @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                                  @RequestHeader(value = "Authorization") String authorization) {
        return new LancamentoResponse(consultaLancamento.executar(id));
    }

    @ApiOperation(value = "Get Entries")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public LancamentosResponse consultarLancamentos(@ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                                    @RequestHeader(value = "Authorization") String authorization) {
        return new LancamentosResponse(consultarLancamentos.executar());
    }

    @ApiOperation(value = "Get Entries paginated v1")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping(path = "/paginated/v1")
    @ResponseStatus(HttpStatus.OK)
    public LancamentosPageResponse pesquisar(Pageable pageable,
                                             @RequestParam(value = "dataVencimentoDe", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dataVencimentoDe,
                                             @RequestParam(value = "dataVencimentoAte", required = false) @DateTimeFormat(iso = ISO.DATE) LocalDate dataVencimentoAte,
                                             @RequestParam(value = "tipoLancamento", required = false) TipoLancamento tipoLancamento,
                                             @RequestParam(value = "descricao", required = false) String descricao,
                                             @ApiParam(required = true, value = "Authorization: Bearer <TOKEN>")
                                             @RequestHeader(value = "Authorization") String authorization) {
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

    @ApiOperation(value = "Get statistics entries by category")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping(path = "/estatistica/por-categoria")
    public LancamentoEstatisticaPorCategoriaResponse porCategoria(Pageable pageable,
                                                                  @RequestHeader(value = "Authorization") String authorization) {
        return new LancamentoEstatisticaPorCategoriaResponse(this.consultaLancamentosPorCategoria.executar(pageable, LocalDate.now()));
    }

    @ApiOperation(value = "Get statistics entries by day")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping(path = "/estatistica/por-dia")
    public LancamentoEstatisticaPorDiaResponse porDia(Pageable pageable,
                                                      @RequestHeader(value = "Authorization") String authorization) {
        return new LancamentoEstatisticaPorDiaResponse(this.consultaLancamentosPorDia.executar(pageable, LocalDate.now()));
    }

    @ApiOperation(value = "Get statistics entries by person")
    @PreAuthorize("hasAuthority('ROLE_PESQUISAR_LANCAMENTO') and #oauth2.hasScope('read')")
    @GetMapping(path = "/estatistica/por-pessoa")
    public LancamentoEstatisticaPorPessoaResponse porPessoa(Pageable pageable,
                                                            @RequestParam(value = "dataVencimentoDe") @DateTimeFormat(iso = ISO.DATE) LocalDate dataVencimentoDe,
                                                            @RequestParam(value = "dataVencimentoAte") @DateTimeFormat(iso = ISO.DATE) LocalDate dataVencimentoAte,
                                                            @RequestHeader(value = "Authorization") String authorization) {
        return new LancamentoEstatisticaPorPessoaResponse(this.consultaLancamentosPorPessoa.executar(pageable, dataVencimentoDe, dataVencimentoAte));
    }


   /* @PostMapping("upload/file")
    @PreAuthorize("hasAuthority('ROLE_CADASTRAR_LANCAMENTO') and hasAuthority('SCOPE_write')")
    public UploadFileResponse uploadFile(@RequestParam MultipartFile file)  {
        String nome = enviarArquivoS3.salvarTemporariamente(file);

        return new UploadFileResponse(nome, enviarArquivoS3.configurarUrl(nome));
    }*/

}
