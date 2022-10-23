package com.serasaexperian.pessoa.map;

import com.serasaexperian.pessoa.dtos.response.ScoreResponseDto;
import com.serasaexperian.pessoa.models.ScoreModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ScoreMapper {

    ScoreMapper INSTANCE = Mappers.getMapper(ScoreMapper.class);

    @Mapping(source = "descricao", target = "scoreDescricao")
    @Mapping(source = "rangeInicial", target = "inicial")
    ScoreResponseDto modelToResponseDto(ScoreModel scoreModel);
}
