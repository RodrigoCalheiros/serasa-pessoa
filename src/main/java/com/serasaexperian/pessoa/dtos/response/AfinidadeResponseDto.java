package com.serasaexperian.pessoa.dtos.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.ArrayList;
import java.util.List;

@Builder
public class AfinidadeResponseDto {
    @JsonProperty
    private String regiao;

    @JsonProperty
    private List<String> estados = new ArrayList<>();
}
