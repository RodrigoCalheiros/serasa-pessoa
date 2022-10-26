package com.serasaexperian.pessoa.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PessoaResponseDto {

    @JsonProperty
    private String nome;

    @JsonProperty
    private String telefone;

    @JsonProperty
    private Integer idade;

    @JsonProperty
    private String cidade;

    @JsonProperty
    private String estado;

    @JsonProperty
    private String scoreDescricao;

    @JsonProperty
    private List<String> estados;

}
