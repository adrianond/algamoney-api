package com.algamoney.api.http.domain.response;

import com.algamoney.api.database.entity.AlgamoneyViewConsulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaViewAlgamoneyResponse {
    private List<AlgamoneyViewConsulta> consultas = new ArrayList<>();
}
