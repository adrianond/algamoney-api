package com.algamoney.api.usecase.relatorio;

import com.algamoney.api.config.MarginConfig;
import com.algamoney.api.http.domain.request.ReportRequest;
import com.algamoney.api.utils.IndiceAlfabetico;
import com.algamoney.api.utils.IndiceRomano;
import com.lowagie.text.DocumentException;
import freemarker.template.*;
import lombok.AllArgsConstructor;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GerarDocumentoPdf {
	private static final String TEMPLATE_DIR = "templates";
	private static final String IMG_DIR = TEMPLATE_DIR + "/commons/img/";

	public InputStreamResource executar(String templateSrc, ReportRequest reportRequest) throws IOException, TemplateException, DocumentException {
		File templatesFile = ResourceUtils.getFile( "classpath:" + TEMPLATE_DIR);

		HashMap<String, Object> infoReport =  reportRequest.getForm();
		infoReport.put("marginConfig", marginConfig(infoReport));
		infoReport.putIfAbsent("logoBase64", generateImagemBase64("logo_omni", "png"));
		setNumeroVias(infoReport);
		Integer numeroCopias = getNumeroCopias(infoReport);

		infoReport.put("romano", new IndiceRomano());
		infoReport.put("alfabetico", new IndiceAlfabetico());

		ITextRenderer pdfRenderer = generatePdfRenderer(templateSrc, templatesFile, infoReport);

		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		pdfRenderer.createPDF(outputStream, false);
		for(int i = 1; i < numeroCopias; i++) {
			pdfRenderer.writeNextDocument(0);
		}
		pdfRenderer.finishPDF();

		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		return new InputStreamResource(inputStream);
	}

	private Integer getNumeroCopias(HashMap<String, Object> infoReport) {
		return extractParameterAsInteger(infoReport, "copias").orElse(1);
	}

	private void setNumeroVias(HashMap<String, Object> infoReport) {
		Optional<Integer> qtdeViasOpt = extractParameterAsInteger(infoReport, "qtdeVias");
		if (qtdeViasOpt.isPresent()) {
			infoReport.put("qtdeVias", new SimpleNumber(qtdeViasOpt.get()));
		}
	}

	private Optional<Integer> extractParameterAsInteger(HashMap<String, Object> infoReport, String pName) {
		Object paramObj = infoReport.get(pName);
		Integer intParam = null;
		if(Objects.nonNull(paramObj)) {
			if (paramObj instanceof String) {
				intParam = Integer.parseInt((String)paramObj);
			}
			if (paramObj instanceof Number) {
				intParam = ((Number)paramObj).intValue();
			}
		}
		return Optional.ofNullable(intParam);
	}

	private MarginConfig marginConfig(HashMap<String, Object> infoReport) {
		MarginConfig marginConfig = new MarginConfig();
		marginConfig.setTop((Double) infoReport.get("marginConfig.top"));
		marginConfig.setBottom((Double) infoReport.get("marginConfig.bottom"));
		marginConfig.setLeft((Double) infoReport.get("marginConfig.left"));
		marginConfig.setRight((Double) infoReport.get("marginConfig.right"));
		return marginConfig;
	}

	private String generateImagemBase64(String imagem, String extensao) throws IOException {
		byte[] imagemByte = FileUtils.readFileToByteArray(
				ResourceUtils.getFile( "classpath:" + IMG_DIR + imagem + "." + extensao));
		return "data:image/" + extensao + ";base64," + Base64.encodeBase64String(imagemByte);
	}

	private ITextRenderer generatePdfRenderer(String templateSrc, File templatesFile, Object params)
			throws IOException, TemplateException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
		cfg.setDefaultEncoding("UTF-8");
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
		cfg.setDirectoryForTemplateLoading(templatesFile);

		Template template = cfg.getTemplate(templateSrc.concat(".html"));

		StringWriter consoleWriter = new StringWriter();
		template.process(params, consoleWriter);

		ITextRenderer renderer = new ITextRenderer();

		renderer.setDocumentFromString(consoleWriter.toString());
		renderer.layout();
		return renderer;
	}
}
