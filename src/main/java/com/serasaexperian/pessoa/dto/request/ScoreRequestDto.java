package com.serasaexperian.pessoa.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class ScoreRequestDto {

    @JsonProperty("scoreDescricao")
    @NotBlank
    @Size(min = 1, max = 20)
    private String descricao;

    @JsonProperty("inicial")
    @Min(0)
    @Max(1000)
    private Integer rangeInicial;

    @JsonProperty("final")
    @Min(0)
    @Max(1000)
    private Integer rangeFinal;

}
