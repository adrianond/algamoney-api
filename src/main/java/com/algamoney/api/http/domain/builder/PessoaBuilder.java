package com.algamoney.api.http.domain.builder;

import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.entity.Telefone;
import com.algamoney.api.database.entity.embedded.Endereco;
import com.algamoney.api.http.domain.EnderecoDTO;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.TelefoneDTO;
import com.algamoney.api.http.domain.TelefoneIdDTO;
import com.algamoney.api.util.DateUtil;
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
                .endereco(buildEnderecoDTO(pessoa.getEndereco()))
                .ativo(pessoa.isAtivo())
                .dataCadastro(DateUtil.formatLocalDateTime(pessoa.getDataCadastro()))
                .dataAtualizacao(DateUtil.formatLocalDateTime(pessoa.getDataAtualizacao()))
                .telefoneDTO(buildTelefoneDTO(pessoa.getTelefones()))
                .build();
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
        if (!telefone.getCategoria().equals(CELULAR) && telefone.getRamal() != null)
            telefoneDTO.setRamal(telefone.getRamal());
        return telefoneDTO;
    }
}
