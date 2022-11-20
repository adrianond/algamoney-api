package com.algamoney.api.http.domain.builder;

import com.algamoney.api.database.entity.Contato;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.Telefone;
import com.algamoney.api.database.entity.embedded.Endereco;
import com.algamoney.api.http.domain.*;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.algamoney.api.database.entity.enumeration.CategoriaTelefone.CELULAR;

@Component
public class PessoaBuilder {

    public PessoaDTO buildPessoaDTO(Pessoa pessoa) {
        return PessoaDTO.builder()
                .id(pessoa.getId())
                .nome(pessoa.getNome())
                .enderecoDTO(buildEnderecoDTO(pessoa.getEndereco()))
                .ativo(pessoa.isAtivo())
                .dataCadastro(pessoa.getDataCadastro())
                .dataAtualizacao(pessoa.getDataAtualizacao())
                .telefones(buildTelefoneDTO(pessoa.getTelefones()))
                .contatos(buildContatod(pessoa.getContatos()))
                .build();
    }

    public String getNomePessoa(Pessoa pessoa) {
        return pessoa.getNome();
    }

    private List<ContatoDTO> buildContatod(List<Contato> contatos) {
        if (CollectionUtils.isEmpty(contatos))
            return Collections.emptyList();

        return contatos.stream().map(contato -> {
            ContatoDTO contatoDTO = new ContatoDTO();
            contatoDTO.setId(contato.getId());
            contatoDTO.setNome(contato.getNome());
            contatoDTO.setEmail(contato.getEmail());
            contatoDTO.setTelefone(contato.getTelefone());
            return contatoDTO;

        }).collect(Collectors.toList());
    }

    private EnderecoDTO buildEnderecoDTO(Endereco endereco) {
        if (endereco == null)
            return null;
        return EnderecoDTO.builder()
                .bairro(endereco.getBairro())
                .cep(endereco.getCep())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .estado(endereco.getEstado())
                .numero(endereco.getNumero())
                .logradouro(endereco.getLogradouro())
                .build();
    }

    private List<TelefoneDTO> buildTelefoneDTO(List<Telefone> telefones) {
        if (CollectionUtils.isEmpty(telefones))
            return Collections.emptyList();
        return telefones.stream()
                .map(this::setTelefone)
                .collect(Collectors.toList());
    }

    private TelefoneDTO setTelefone(Telefone telefone) {
        TelefoneDTO telefoneDTO = new TelefoneDTO();
        telefoneDTO.setId(new TelefoneIdDTO(telefone.getId().getPessoa(), telefone.getId().getSequencia()));
        telefoneDTO.setNumero(telefone.getNumero());
        telefoneDTO.setCategoriaTelefone(telefone.getCategoria());
        if (!telefone.getCategoria().equals(CELULAR) && telefone.getRamal() != null)
            telefoneDTO.setRamal(telefone.getRamal());
        return telefoneDTO;
    }
}
