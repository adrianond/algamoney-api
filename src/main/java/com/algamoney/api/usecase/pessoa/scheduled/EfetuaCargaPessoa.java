package com.algamoney.api.usecase.pessoa.scheduled;

import com.algamoney.api.exception.CargaPessoaException;
import com.algamoney.api.usecase.batch.CargaPessoaJob;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(value = "app.algamoney.scheduler.efetuar-carga-pessoa.enabled", havingValue = "true")
public class EfetuaCargaPessoa {
    //private final Job cargaPessoaJob;
    private final JobLauncher jobLauncher;
    private final CargaPessoaJob cargaPessoaJob;

    //@Scheduled(fixedDelay = 5000)
    @Scheduled(cron = "${app.algamoney.scheduler.efetuar-carga-pessoa.cron}")
    public void executar() {
        try {
            JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            //var jobExecution = jobLauncher.run(cargaPessoaJob, jobParameters);

            JobExecution jobExecution = jobLauncher.run(cargaPessoaJob.executarJob(), jobParameters);
            if (!ExitStatus.COMPLETED.getExitCode().equals(jobExecution.getExitStatus().getExitCode())) {
                System.out.println("Job não executado!");
                throw new Exception(jobExecution.getAllFailureExceptions().get(0));
            }
        } catch (Exception e) {
            log.error("Ocorreu erro efetuar a carga de pessoa", e);
            throw new CargaPessoaException(e.getMessage(), e);
        }

    }

}
