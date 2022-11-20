package com.algamoney.api.http;


import com.algamoney.api.usecase.relatorio.GerarRelatorioEstatisticaPorPessoa;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@AllArgsConstructor
@RequestMapping("api/report")
public class ReportPdfWS {
    private final GerarRelatorioEstatisticaPorPessoa gerarRelatorioEstatisticaPorPessoa;

    @PostMapping(value = "/statistic/person/download", produces = MediaType.APPLICATION_PDF_VALUE)
    public InputStreamResource gerarRelatorioEstaticaPorPessoa(
            @RequestParam(value = "dataVencimentoDe") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimentoDe,
            @RequestParam(value = "dataVencimentoAte") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataVencimentoAte) throws Exception {

        return gerarRelatorioEstatisticaPorPessoa.executar(dataVencimentoDe, dataVencimentoAte);
    }
}