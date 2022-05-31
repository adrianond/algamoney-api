package com.algamoney.api.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JdbcPageable {
    private JdbcTemplate jdbcTemplate;

    public <T> List<T> list(String sql, Class<T> clazz, Sort sort) {
        return jdbcTemplate.query(querySorted(sql, sort), new BeanPropertyRowMapper<>(clazz));
    }

    private <T> List<T> list(String sql, Class<T> clazz) {
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(clazz));
    }

    public <T> Page<T> page(String sql, Class<T> clazz, Pageable page) {
        int start = page.getPageSize() * page.getPageNumber();
        int end = start + page.getPageSize();
        String paged = String.format(
                "SELECT * FROM (%s) WHERE R BETWEEN %d AND %d",
                rownumSorted(sql, page.getSort()),
                start + 1,
                end);
        return new PageImpl<>(list(paged, clazz), page, count(sql));
    }

    private String rownumSorted(String sql, Sort sort) {
        return String.format("SELECT ROWNUM R, Q.* FROM (%s) Q", querySorted(sql, sort));
    }

    private String querySorted(String sql, Sort sort) {
        if (sort.isSorted())
            sql += sort.stream()
                    .map(o -> String.format("%s %s", o.getProperty(), o.getDirection()))
                    .collect(Collectors.joining(", ", " ORDER BY ", ""));
        return sql;
    }

    public Long count(String sql) {
        return jdbcTemplate.queryForObject(String.format("SELECT COUNT(*) FROM (%s)", sql), Long.class);
    }
}