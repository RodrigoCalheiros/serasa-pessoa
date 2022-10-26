package com.serasaexperian.pessoa.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
public class AfinidadeResponseDto {
    @JsonProperty
    private String regiao;

    @JsonProperty
    private List<String> estados = new ArrayList<>();
}
