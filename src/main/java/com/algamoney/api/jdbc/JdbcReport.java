package com.algamoney.api.jdbc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class JdbcReport<R, F> {
    private JdbcPageable jdbcPageable;
    private Class<R> clazz;

    protected JdbcReport(JdbcPageable jdbcPageable, Class<R> clazz) {
        this.jdbcPageable = jdbcPageable;
        this.clazz = clazz;
    }

    public Page<R> executar(F filters, Pageable page) {

        return jdbcPageable.page(query(filters), clazz, page);
    }

    public List<R> executar(F filters, Sort sort) {

        return jdbcPageable.list(query(filters), clazz, sort);
    }

    protected abstract String query(F filters);
}