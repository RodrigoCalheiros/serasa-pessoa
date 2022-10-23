package com.serasaexperian.pessoa.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AfinidadeDto {

    //TODO validação se a região já existe
    @NotBlank
    @Size(min=1, max = 20)
    private String regiao;

    //TODO validação se estado não existente
    @NotEmpty
    @Size(min = 1)
    private List<String> estados = new ArrayList<>();
}
