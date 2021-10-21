package com.algamoney.api.usecase.endereco;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.embedded.Endereco;
import com.algamoney.api.http.domain.request.EnderecoRequest;
import org.springframework.stereotype.Component;

@Component
public class CadastrarEndereco {

    public void executar(Pessoa pessoa, EnderecoRequest request) {
        if (request != null) {
            Endereco endereco = pessoa.getEndereco() != null ? pessoa.getEndereco() : new Endereco();
            endereco.setBairro(request.getBairro());
            endereco.setCep(request.getCep());
            endereco.setCidade(request.getCidade());
            endereco.setComplemento(request.getComplemento());
            endereco.setEstado(request.getEstado());
            endereco.setLogradouro(request.getLogradouro());
            endereco.setNumero(request.getNumero());
            pessoa.setEndereco(endereco);
        }
    }
}
