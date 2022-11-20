package com.algamoney.api.usecase.pessoa;

import com.algamoney.api.database.entity.Contato;
import com.algamoney.api.database.entity.Pessoa;
import com.algamoney.api.database.repository.PessoaRepositoryFacade;
import com.algamoney.api.http.domain.PessoaDTO;
import com.algamoney.api.http.domain.builder.PessoaBuilder;
import com.algamoney.api.http.domain.request.PessoaRequest;
import com.algamoney.api.usecase.endereco.CadastrarEndereco;
import com.algamoney.api.usecase.telefone.CadastrarTelefone;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional
public class PersistirPessoa {
    private final PessoaRepositoryFacade pessoaRepositoryFacade;
    private final PessoaBuilder pessoaBuilder;
    private final CadastrarEndereco cadastrarEndereco;
    private final CadastrarTelefone cadastrarTelefone;

    public PessoaDTO executar(PessoaRequest request) {
        return build(null, request);
    }

    public PessoaDTO executar(Long idPessoa, PessoaRequest request) {
        return build(pessoaRepositoryFacade.findById(idPessoa), request);
    }

    public PessoaDTO build(Pessoa pessoa, PessoaRequest request) {
        PessoaDTO pessoaDTO = request.getPessoaDTO();
        if (null == pessoa) {
            pessoa = new Pessoa();
            pessoa.setDataCadastro(LocalDateTime.now());
        }
        pessoa.setNome(pessoaDTO.getNome());
        pessoa.setAtivo(pessoaDTO.isAtivo());
        pessoa.setDataAtualizacao(LocalDateTime.now());

        cadastrarEndereco.executar(pessoa, pessoaDTO);
        buildContatosPessoa(pessoa, pessoaDTO);
        Pessoa p = pessoaRepositoryFacade.add(pessoa);
        cadastrarTelefone.executar(p, pessoaDTO);
        return pessoaBuilder.buildPessoaDTO(p);
    }

    private void buildContatosPessoa(Pessoa pessoa, PessoaDTO pessoaDTO) {
        if (CollectionUtils.isEmpty(pessoaDTO.getContatos()))
            return;

        pessoa.getContatos().clear();
        pessoa.getContatos().addAll(getContatos(pessoa, pessoaDTO));
    }

    private List<Contato> getContatos(Pessoa pessoa, PessoaDTO pessoaDTO) {
        return pessoaDTO.getContatos().stream()
                .map(contatoDTO -> {
                    Contato contato = new Contato();
                    contato.setNome(contatoDTO.getNome());
                    contato.setEmail(contatoDTO.getEmail());
                    contato.setTelefone(contatoDTO.getTelefone());
                    contato.setPessoa(pessoa);
                    return contato;
                }).collect(Collectors.toList());

    }


}
