package com.serasaexperian.pessoa.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class AfinidadeRequestDto {

    @NotBlank
    @Size(min=1, max = 20)
    private String regiao;

    @NotEmpty
    @Size(min = 1)
    private List<String> estados = new ArrayList<>();
}
