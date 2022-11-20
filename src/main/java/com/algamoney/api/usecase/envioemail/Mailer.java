package com.algamoney.api.usecase.envioemail;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Usuario;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class Mailer {
    private final JavaMailSender mailSender;
    private final TemplateEngine thymeleaf;


    public void avisarSobreLancamentosVencidos(List<Lancamento> vencidos, List<Usuario> destinatarios) {
        log.info("Preparando o envio de email de aviso de lançamentos vencidos.Há {} lançamentos vencidos", vencidos.size());

        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("lancamentos", vencidos);

        List<String> emails = destinatarios.stream()
                .map(u -> u.getEmail())
                .collect(Collectors.toList());

        this.enviarEmail("adriano.dantas@omni.com.br", emails, "Lançamentos vencidos", "aviso-lancamentos-vencidos", variaveis);
    }

    public void enviarEmail(String remetente, List<String> destinatarios, String assunto, String template, Map<String, Object> variaveis) {
        Context context = new Context(new Locale("pt", "BR"));

        variaveis.entrySet()
                .forEach(e -> context.setVariable(e.getKey(), e.getValue()));

        String mensagem = thymeleaf.process(template, context);

        try {
           /* MimeMessage mimeMessage = mailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
            helper.setFrom(remetente);
            helper.setTo(destinatarios.toArray(new String[destinatarios.size()]));
            helper.setSubject(assunto);
            helper.setText(mensagem, true);

            mailSender.send(mimeMessage);*/




           /* HtmlEmail htmlEmail = new HtmlEmail();
            htmlEmail.setHtmlMsg(mensagem);
            htmlEmail.addTo("adrianond@yahoo.com.br");
            htmlEmail.setSubject(assunto);
            htmlEmail.setFrom("adrianond@yahoo.com.br");
            htmlEmail.setHostName("smtp.mail.yahoo.com");
            htmlEmail.setSmtpPort(587);
            htmlEmail.setTLS(true);
            htmlEmail.setSSL(true);
            htmlEmail.setAuthenticator(new DefaultAuthenticator("adrianond@yahoo.com.br", "Tricolor_1992"));
            htmlEmail.send();*/







            log.info("Envio de email de lançamentos vencidos concluido!");
        } catch (Exception e) {
            log.error("Problemas com o envio de e-mail!", e);
            throw new RuntimeException("Problemas com o envio de e-mail!", e);
        }
    }
}
