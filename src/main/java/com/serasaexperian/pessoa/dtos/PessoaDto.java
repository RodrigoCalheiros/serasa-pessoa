package com.serasaexperian.pessoa.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PessoaDto {

    @NotBlank
    @Size(min=1, max = 70)
    private String nome;

    @NotBlank
    @Size(min=1, max = 20)
    private String telefone;

    @Min(value = 0)
    private Integer idade;

    @NotBlank
    @Size(min=1, max = 30)
    private String cidade;

    @NotBlank
    @Size(min = 1, max = 20)
    private String estado;

    @NotBlank
    @Size(min=1, max = 20)
    private String regiao;
}