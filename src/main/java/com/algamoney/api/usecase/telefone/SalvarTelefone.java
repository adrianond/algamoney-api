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

@Component
@AllArgsConstructor
public class SalvarTelefone {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;

    @Transactional
    public void executar(SalvarTelefonePessoa salvarTelefonePessoa) {
        Pessoa pessoa = pessoaRepositoryFacade.findById(salvarTelefonePessoa.getId());
        if (!CollectionUtils.isEmpty(salvarTelefonePessoa.getTelefones()))
            buildTelefones(pessoa, salvarTelefonePessoa.getTelefones());
    }

    private void buildTelefones(Pessoa pessoa, List<TelefoneRequest> telefones) {
        telefones.stream()
                .map(this::getTelefone)
                .forEach(telefone -> pessoa.adicionaTelefone(telefone));
    }

    private Telefone getTelefone(TelefoneRequest telefoneRequest) {
        Telefone telefone = new Telefone();
        telefone.setCategoria(telefoneRequest.getCategoriaTelefone());
        telefone.setRamal(telefoneRequest.getRamal());
        telefone.setNumero(telefoneRequest.getNumero());
        return telefone;
    }
}
