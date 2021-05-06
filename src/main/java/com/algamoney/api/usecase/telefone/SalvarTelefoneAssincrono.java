package com.algamoney.api.usecase.telefone;

import com.algamoney.api.config.amqp.domain.SalvarTelefonePessoa;
import com.algamoney.api.http.domain.request.TelefoneRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.algamoney.api.config.amqp.Queues.SALVA_TELEFONE_PESSOA_QUEUE;

@Component
@AllArgsConstructor
public class SalvarTelefoneAssincrono {
    private final SendMessage sendMessage;

    public void executar(Long id, List<TelefoneRequest> telefones) {
      sendMessage.enviar(new SalvarTelefonePessoa(id, telefones), SALVA_TELEFONE_PESSOA_QUEUE);
    }
}
