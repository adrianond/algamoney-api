package com.algamoney.api.usecase.endereco;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.embedded.Endereco;
import com.algamoney.api.http.domain.EnderecoDTO;
import com.algamoney.api.http.domain.PessoaDTO;
import org.springframework.stereotype.Component;

@Component
public class CadastrarEndereco {

    public void executar(Pessoa pessoa, PessoaDTO pessoaDTO) {
        EnderecoDTO enderecoDTO = pessoaDTO.getEnderecoDTO();
        if (enderecoDTO != null) {
            Endereco endereco = pessoa.getEndereco() != null ? pessoa.getEndereco() : new Endereco();
            endereco.setBairro(enderecoDTO.getBairro());
            endereco.setCep(enderecoDTO.getCep());
            endereco.setCidade(enderecoDTO.getCidade());
            endereco.setComplemento(enderecoDTO.getComplemento());
            endereco.setEstado(enderecoDTO.getEstado());
            endereco.setLogradouro(enderecoDTO.getLogradouro());
            endereco.setNumero(enderecoDTO.getNumero());
            pessoa.setEndereco(endereco);
        }
    }
}
