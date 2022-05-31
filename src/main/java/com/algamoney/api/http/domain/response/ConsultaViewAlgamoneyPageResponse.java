package com.algamoney.api.http.domain.response;

import com.algamoney.api.database.entity.AlgamoneyViewConsulta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaViewAlgamoneyPageResponse {
    private Page<AlgamoneyViewConsulta> consultas;
}
