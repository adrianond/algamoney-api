package com.algamoney.api.config.batch;

import lombok.var;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.validation.DataBinder;

import java.beans.PropertyEditorSupport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BeanWrapperFieldSetMapperCustom extends BeanWrapperFieldSetMapper {

    @Override
    protected void initBinder(DataBinder binder) {
        var formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        binder.registerCustomEditor(LocalDate.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text != null && !text.isEmpty()) {
                    setValue(LocalDate.parse(text, formatter));
                } else {
                    setValue(null);
                }
            }

            @Override
            public String getAsText() {
                Object date = getValue();
                if (date != null) {
                    return formatter.format((LocalDate) date);
                } else {
                    return "";
                }
            }
        });
    }
}
