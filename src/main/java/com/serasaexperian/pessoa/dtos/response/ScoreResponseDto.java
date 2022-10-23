package com.serasaexperian.pessoa.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScoreResponseDto {
    @JsonProperty()
    private String scoreDescricao;
    @JsonProperty()
    private Integer inicial;
    @JsonProperty("final")
    private Integer rangeFinal;
}
