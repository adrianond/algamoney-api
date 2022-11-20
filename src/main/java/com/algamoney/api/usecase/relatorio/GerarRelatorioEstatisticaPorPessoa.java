package com.algamoney.api.usecase.relatorio;

import com.algamoney.api.http.domain.LancamentoEstatisticaPorPessoaDTO;
import com.algamoney.api.http.domain.request.ReportRequest;
import com.algamoney.api.usecase.lancamento.ConsultarLancamentosPorPessoa;
import com.lowagie.text.DocumentException;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import static com.algamoney.api.utils.AlgamoneyUtils.formatLocalDate;

@Component
@AllArgsConstructor
@Slf4j
public class GerarRelatorioEstatisticaPorPessoa {
    private final ConsultarLancamentosPorPessoa consultarLancamentosPorPessoa;
    private final GerarDocumentoPdf gerarDocumentoPdf;

    public InputStreamResource executar(LocalDate dataVencimentoDe, LocalDate dataVencimentoAte) throws DocumentException, TemplateException, IOException {
        List<LancamentoEstatisticaPorPessoaDTO> list = consultarLancamentosPorPessoa.executarConsulta(Pageable.unpaged(), dataVencimentoDe, dataVencimentoAte);

        try {
            return gerarDocumentoPdf.executar("relatorio_estatistica_por_pessoa", buildReportRequest(dataVencimentoDe, dataVencimentoAte, list));
        } catch (Exception e) {
            log.error("Erro ao gerar relatorio " + e);
            throw e;
        }
    }

    private ReportRequest buildReportRequest(LocalDate dataInicial, LocalDate dataFinal, List<LancamentoEstatisticaPorPessoaDTO> list) {
        ReportRequest reportRequest = new ReportRequest();
        HashMap<String, Object> form = new HashMap<>();
        form.put("dataInicial", formatLocalDate(dataInicial));
        form.put("dataFinal", formatLocalDate(dataFinal));
        form.put("listEstaticaPorPesoa", list);
        reportRequest.setForm(form);

        return reportRequest;
    }

}
