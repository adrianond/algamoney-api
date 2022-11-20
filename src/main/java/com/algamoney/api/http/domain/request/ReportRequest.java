package com.algamoney.api.http.domain.request;

import lombok.Data;

import javax.validation.Valid;
import java.util.HashMap;

@Data
public class ReportRequest {

    @Valid
    private HashMap<String, Object> form;

}
