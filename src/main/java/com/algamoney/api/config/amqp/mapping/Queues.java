package com.algamoney.api.config.amqp.mapping;

public final class Queues {
    private Queues() {
        //default constructor
    }

    public static final String SALVA_TELEFONE_PESSOA_QUEUE = "algamoney.event.save.phone.queue";
    public static final String CARGA_PESSOA_QUEUE = "algamoney.event.carga.pessoa.queue";
}