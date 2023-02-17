package com.algamoney.api.usecase.batch;

import com.algamoney.api.config.batch.CargaPessoaWriter;
import com.algamoney.api.config.batch.JobLoggerListener;
import com.algamoney.api.http.domain.CargaPessoaDTO;



import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.listener.JobListenerFactoryBean;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CargaPessoaJob {

    @Value("${app.algamoney.job.tamanhoLote}")
    private int tamanhoLote;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private FlatFileItemReader<CargaPessoaDTO> cargaPessoaReader;

    @Autowired
    private CargaPessoaWriter cargaPessoaWriter;


    public Job executarJob() {
        JobRepository jobRepository = context.getBean(JobRepository.class);
        //TODO - implementar restart do job do ponto de falha
        return new JobBuilderFactory(jobRepository).get("cargaPessoaJob_"+ LocalDateTime.now())
                .incrementer(new RunIdIncrementer())
                .listener(JobListenerFactoryBean.getListener(new JobLoggerListener()))
                .flow(stepCarga())
                .end()
                .build();
    }


    public Step stepCarga() {
        return stepBuilderFactory.get("stepCarga")
                .<CargaPessoaDTO, CargaPessoaDTO>chunk(tamanhoLote)
                .reader(cargaPessoaReader)
                .writer(cargaPessoaWriter)
                .build();
    }
}

