package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.dtos.request.AfinidadeRequestDto;
import com.serasaexperian.pessoa.dtos.response.AfinidadeResponseDto;
import com.serasaexperian.pessoa.dtos.response.ScoreResponseDto;
import com.serasaexperian.pessoa.models.AfinidadeModel;
import com.serasaexperian.pessoa.models.ScoreModel;
import com.serasaexperian.pessoa.repositories.AfinidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class AfinidadeService {

    @Autowired
    AfinidadeRepository afinidadeRepository;

    @Autowired
    EstadoService estadoService;

    public AfinidadeResponseDto save(final AfinidadeRequestDto afinidadeRequestDto) {
        AfinidadeModel afinidadeModel = new AfinidadeModel();
        BeanUtils.copyProperties(afinidadeRequestDto, afinidadeModel);
        afinidadeModel.getEstados().addAll(
                afinidadeRequestDto.getEstados().stream().map(
                        estado -> estadoService.findBySigla(estado)).collect(Collectors.toList()
                )
        );
        return modelToResponseDto(afinidadeRepository.save(afinidadeModel));
    }

    public AfinidadeModel findByRegiao(final String regiao){
        return afinidadeRepository.findOneByRegiao(regiao);
    }

    private AfinidadeResponseDto modelToResponseDto(final AfinidadeModel afinidadeModel){
        return AfinidadeResponseDto.builder()
                .regiao(afinidadeModel.getRegiao())
                .estados(afinidadeModel.getEstados().stream().map(estado -> estado.getSigla()).collect(Collectors.toList()))
                .build();
    }
}
