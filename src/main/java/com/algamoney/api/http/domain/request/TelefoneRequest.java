package com.algamoney.api.http.domain.request;

import com.algamoney.api.http.domain.TelefoneDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TelefoneRequest {
    @Valid
    @NotNull
   private List<TelefoneDTO> telefoneDTOList;
}
