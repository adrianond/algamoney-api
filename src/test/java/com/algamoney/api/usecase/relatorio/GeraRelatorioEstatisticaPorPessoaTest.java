package com.algamoney.api.usecase.relatorio;

import com.algamoney.api.http.domain.LancamentoEstatisticaPorPessoaDTO;
import com.algamoney.api.http.domain.request.ReportRequest;
import com.algamoney.api.usecase.lancamento.ConsultaLancamentosPorPessoa;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GeraRelatorioEstatisticaPorPessoaTest {

    @Mock
    private ConsultaLancamentosPorPessoa consultaLancamentosPorPessoa;

    @Mock
    private GeraDocumentoPdf geraDocumentoPdf;

    @InjectMocks
    private GeraRelatorioEstatisticaPorPessoa geraRelatorioEstatisticaPorPessoa;

    @Test
    public void deveGerarRelatorioComSucesso() throws Exception {
        LocalDate de = LocalDate.of(2024, 1, 1);
        LocalDate ate = LocalDate.of(2024, 1, 31);

        List<LancamentoEstatisticaPorPessoaDTO> lista = Collections.emptyList();
        InputStreamResource resourceMock = new InputStreamResource(new ByteArrayInputStream(new byte[0]));

        when(consultaLancamentosPorPessoa.executarConsulta(any(Pageable.class), eq(de), eq(ate)))
                .thenReturn(lista);
        when(geraDocumentoPdf.executar(eq("relatorio_estatistica_por_pessoa"), any(ReportRequest.class)))
                .thenReturn(resourceMock);

        InputStreamResource resultado = geraRelatorioEstatisticaPorPessoa.executar(de, ate);

        assertNotNull(resultado);
        verify(consultaLancamentosPorPessoa).executarConsulta(any(Pageable.class), eq(de), eq(ate));
        verify(geraDocumentoPdf).executar(eq("relatorio_estatistica_por_pessoa"), any(ReportRequest.class));
    }

    @Test
    public void deveMontarReportRequestComDatasCorretasELista() throws Exception {
        LocalDate de = LocalDate.of(2024, 2, 1);
        LocalDate ate = LocalDate.of(2024, 2, 29);

        List<LancamentoEstatisticaPorPessoaDTO> lista = Collections.emptyList();
        InputStreamResource resourceMock = new InputStreamResource(new ByteArrayInputStream(new byte[0]));

        when(consultaLancamentosPorPessoa.executarConsulta(any(Pageable.class), eq(de), eq(ate)))
                .thenReturn(lista);

        ArgumentCaptor<ReportRequest> reportCaptor = ArgumentCaptor.forClass(ReportRequest.class);
        when(geraDocumentoPdf.executar(any(), reportCaptor.capture())).thenReturn(resourceMock);

        geraRelatorioEstatisticaPorPessoa.executar(de, ate);

        ReportRequest reportRequest = reportCaptor.getValue();
        assertNotNull(reportRequest.getForm().get("dataInicial"));
        assertNotNull(reportRequest.getForm().get("dataFinal"));
        assertNotNull(reportRequest.getForm().get("listEstaticaPorPesoa"));
    }
}
