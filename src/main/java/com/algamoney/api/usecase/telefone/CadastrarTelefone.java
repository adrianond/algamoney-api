package com.algamoney.api.usecase.telefone;

import com.algamoney.api.config.amqp.domain.SalvarTelefonePessoa;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.Telefone;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.request.TelefoneRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@AllArgsConstructor
public class CadastrarTelefone {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;

    public void executar(Pessoa pessoa, List<TelefoneRequest> telefones) {
        if (!CollectionUtils.isEmpty(telefones)) {
            pessoa.getTelefones().clear();
            buildTelefones(pessoa, telefones);
        }
    }

    @Transactional
    public void executar(SalvarTelefonePessoa salvarTelefonePessoa) {
        if (!CollectionUtils.isEmpty(salvarTelefonePessoa.getTelefones())) {
            Pessoa pessoa = pessoaRepositoryFacade.findById(salvarTelefonePessoa.getId());
            buildTelefones(pessoa, salvarTelefonePessoa.getTelefones());
        }
    }

    private void buildTelefones(Pessoa pessoa, List<TelefoneRequest> telefonesRequests) {
        AtomicInteger sequencia = new AtomicInteger(telefonesRequests.stream()
                .mapToInt(TelefoneRequest::getSequencia)
                .max().orElse(0) + 1);

        telefonesRequests.stream()
                .map(this::buildTelefone)
                .forEach(telefone -> pessoa.adicionaTelefone(telefone, sequencia.getAndIncrement()));
    }

    private Telefone buildTelefone(TelefoneRequest telefoneRequest) {
        Telefone telefone = new Telefone();
        telefone.setCategoria(telefoneRequest.getCategoriaTelefone());
        telefone.setRamal(telefoneRequest.getRamal());
        telefone.setNumero(telefoneRequest.getNumero());
        return telefone;
    }
}
