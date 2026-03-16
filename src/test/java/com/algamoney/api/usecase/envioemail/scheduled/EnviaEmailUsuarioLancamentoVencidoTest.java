package com.algamoney.api.usecase.envioemail.scheduled;

import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.database.entity.Usuario;
import com.algamoney.api.database.repository.LancamentoRepositoryFacade;
import com.algamoney.api.database.repository.UsuarioRepositoryFacade;
import com.algamoney.api.usecase.envioemail.Mailer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EnviaEmailUsuarioLancamentoVencidoTest {

    @Mock
    private LancamentoRepositoryFacade lancamentoRepositoryFacade;

    @Mock
    private UsuarioRepositoryFacade usuarioRepositoryFacade;

    @Mock
    private Mailer mailer;

    @InjectMocks
    private EnviaEmailUsuarioLancamentoVencido enviaEmailUsuarioLancamentoVencido;

    @Test
    public void deveEnviarEmailQuandoHaLancamentosVencidos() {
        List<Lancamento> vencidos = Collections.singletonList(new Lancamento());
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@email.com");
        List<Usuario> destinatarios = Collections.singletonList(usuario);

        when(lancamentoRepositoryFacade.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(any(LocalDate.class)))
                .thenReturn(vencidos);
        when(usuarioRepositoryFacade.findByPermissoesDescricao("ROLE_PESQUISAR_LANCAMENTO"))
                .thenReturn(destinatarios);

        enviaEmailUsuarioLancamentoVencido.executar();

        verify(mailer).avisarSobreLancamentosVencidos(vencidos, destinatarios);
    }

    @Test
    public void naoDeveEnviarEmailQuandoNaoHaLancamentosVencidos() {
        when(lancamentoRepositoryFacade.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(any(LocalDate.class)))
                .thenReturn(Collections.emptyList());

        enviaEmailUsuarioLancamentoVencido.executar();

        verify(mailer, never()).avisarSobreLancamentosVencidos(any(), any());
        verify(usuarioRepositoryFacade, never()).findByPermissoesDescricao(any());
    }

    @Test
    public void naoDeveEnviarEmailQuandoNaoHaDestinatarios() {
        List<Lancamento> vencidos = Collections.singletonList(new Lancamento());

        when(lancamentoRepositoryFacade.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(any(LocalDate.class)))
                .thenReturn(vencidos);
        when(usuarioRepositoryFacade.findByPermissoesDescricao("ROLE_PESQUISAR_LANCAMENTO"))
                .thenReturn(Collections.emptyList());

        enviaEmailUsuarioLancamentoVencido.executar();

        verify(mailer, never()).avisarSobreLancamentosVencidos(any(), any());
    }

    @Test
    public void deveEnviarEmailParaTodosOsDestinatarios() {
        Lancamento l1 = new Lancamento();
        Lancamento l2 = new Lancamento();
        List<Lancamento> vencidos = Arrays.asList(l1, l2);

        Usuario u1 = new Usuario();
        u1.setEmail("admin1@email.com");
        Usuario u2 = new Usuario();
        u2.setEmail("admin2@email.com");
        List<Usuario> destinatarios = Arrays.asList(u1, u2);

        when(lancamentoRepositoryFacade.findByDataVencimentoLessThanEqualAndDataPagamentoIsNull(any(LocalDate.class)))
                .thenReturn(vencidos);
        when(usuarioRepositoryFacade.findByPermissoesDescricao("ROLE_PESQUISAR_LANCAMENTO"))
                .thenReturn(destinatarios);

        enviaEmailUsuarioLancamentoVencido.executar();

        verify(mailer).avisarSobreLancamentosVencidos(vencidos, destinatarios);
    }
}
