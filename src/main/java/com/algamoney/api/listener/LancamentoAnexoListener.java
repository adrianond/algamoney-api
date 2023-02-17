/*
package com.algamoney.api.listener;

import com.algamoney.api.AlgamoneyApiApplication;
import com.algamoney.api.database.entity.Lancamento;
import com.algamoney.api.usecase.cloud.EnviarArquivoS3;
import org.springframework.util.StringUtils;

import javax.persistence.PostLoad;

public class LancamentoAnexoListener {

    @PostLoad
    public void postLoad(Lancamento lancamento) {
        if (StringUtils.hasText(lancamento.getAnexo())) {
            EnviarArquivoS3 s3 = AlgamoneyApiApplication.getBean(EnviarArquivoS3.class);
            lancamento.setUrl(s3.configurarUrl(lancamento.getAnexo()));
        }
    }
}
*/
