package com.algamoney.api.http.domain.request;

import com.algamoney.api.http.domain.TelefoneDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TelefoneRequest {
    @Valid
    @NotNull
   private List<TelefoneDTO> telefoneDTOList;
}
