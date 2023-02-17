package com.algamoney.api.usecase.envioemail.scheduled;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Usuario;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.database.repository.UsuarioRepositoryFacade;
import com.algamoney.api.usecase.envioemail.Mailer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
@Transactional
public class EnviarEmailUsuarioLancamentoVencido {
    private static final String DESTINATARIOS = "ROLE_PESQUISAR_LANCAMENTO";
    private final LancamentoRepositoryFacade lancamentoRepositoryFacade;
    private final UsuarioRepositoryFacade usuarioRepositoryFacade;
    private final Mailer mailer;

    //@Scheduled(cron = "0 0 6 * * *")
    @Scheduled(fixedDelay = 5000)
    public void executar() {
        List<Lancamento> vencidos = lancamentoRepositoryFacade.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(LocalDate.now());
        log.info("Pesquisando por lançamentos vencidos para enviar email de aviso!");
        if (CollectionUtils.isEmpty(vencidos)) {
            log.info("Não há lançamentos vencidos para envio de email!");
            return;
        }
        List<Usuario> destinatarios = usuarioRepositoryFacade.findByPermissoesDescricao(DESTINATARIOS);
        if (CollectionUtils.isEmpty(destinatarios))
            log.info("Não há destinatarios para envio de e-mail!");
        else
            mailer.avisarSobreLancamentosVencidos(vencidos, destinatarios);
    }
 }
