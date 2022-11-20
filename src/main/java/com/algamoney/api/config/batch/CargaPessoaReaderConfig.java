package com.algamoney.api.config.batch;

import com.algamoney.api.http.domain.CargaPessoaDTO;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class CargaPessoaReaderConfig {

    @Bean
    public FlatFileItemReader<CargaPessoaDTO> cargaPessoaReader() {
        BeanWrapperFieldSetMapper<CargaPessoaDTO> mapper = new BeanWrapperFieldSetMapper<>();
        mapper.setTargetType(CargaPessoaDTO.class);
        String pessoa [] = {"nome","ativo","logradouro","numero","complemento","bairro","cep","cidade", "estado"};

        return new FlatFileItemReaderBuilder<CargaPessoaDTO>()
                .name("cargaPessoaItemReader")
                .resource(new ClassPathResource("pessoas.csv"))
                .delimited()
                .delimiter(",")
                .quoteCharacter('"')
                .names(pessoa)
                .linesToSkip(1)
                .fieldSetMapper(mapper)
                .build();
    }
}


