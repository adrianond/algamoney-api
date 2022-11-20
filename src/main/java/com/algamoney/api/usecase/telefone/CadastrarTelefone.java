package com.algamoney.api.usecase.telefone;

import com.algamoney.api.config.amqp.domain.SalvarTelefonePessoaMessage;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.Telefone;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.TelefoneDTO;
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

    public void executar(Pessoa pessoa, PessoaDTO pessoaDTO) {
        List<TelefoneDTO> telefones = pessoaDTO.getTelefones();
        if (!CollectionUtils.isEmpty(telefones)) {
            pessoa.getTelefones().clear();
            buildTelefones(pessoa, telefones);
        }
    }

    @Transactional
    public void executar(SalvarTelefonePessoaMessage salvarTelefonePessoaMessage) {
        if (!CollectionUtils.isEmpty(salvarTelefonePessoaMessage.getTelefones())) {
            Pessoa pessoa = pessoaRepositoryFacade.findById(salvarTelefonePessoaMessage.getId());
            buildTelefones(pessoa, salvarTelefonePessoaMessage.getTelefones());
        }
    }

    private void buildTelefones(Pessoa pessoa,List<TelefoneDTO> telefoneDTOS) {
        AtomicInteger sequencia = new AtomicInteger(telefoneDTOS.stream()
                .mapToInt(TelefoneDTO::getSequencia)
                .max().orElse(0) + 1);

        telefoneDTOS.stream()
                .map(this::buildTelefone)
                .forEach(telefone -> pessoa.adicionaTelefone(telefone, sequencia.getAndIncrement()));
    }

    private Telefone buildTelefone(TelefoneDTO telefoneDTO) {
        Telefone telefone = new Telefone();
        telefone.setCategoria(telefoneDTO.getCategoriaTelefone());
        telefone.setRamal(telefoneDTO.getRamal());
        telefone.setNumero(telefoneDTO.getNumero());
        return telefone;
    }
}
