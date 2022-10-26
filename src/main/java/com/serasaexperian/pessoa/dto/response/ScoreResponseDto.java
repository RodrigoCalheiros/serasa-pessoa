package com.serasaexperian.pessoa.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ScoreResponseDto {
    @JsonProperty()
    private String scoreDescricao;
    @JsonProperty("inicial")
    private Integer rangeInicial;
    @JsonProperty("final")
    private Integer rangeFinal;
}
