package com.serasaexperian.pessoa.service;

import com.serasaexperian.pessoa.dto.request.AfinidadeRequestDto;
import com.serasaexperian.pessoa.dto.response.AfinidadeResponseDto;
import com.serasaexperian.pessoa.model.AfinidadeModel;
import com.serasaexperian.pessoa.model.EstadoModel;
import com.serasaexperian.pessoa.repository.AfinidadeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AfinidadeService {

    @Autowired
    AfinidadeRepository afinidadeRepository;

    @Autowired
    EstadoService estadoService;

    public AfinidadeService(AfinidadeRepository afinidadeRepository, EstadoService estadoService) {
        this.afinidadeRepository = afinidadeRepository;
        this.estadoService = estadoService;
    }


    public AfinidadeResponseDto save(final AfinidadeRequestDto afinidadeRequestDto) {
        AfinidadeModel afinidadeModel = new AfinidadeModel();
        BeanUtils.copyProperties(afinidadeRequestDto, afinidadeModel);
        afinidadeModel.getEstados().addAll(
                afinidadeRequestDto.getEstados().stream().map( estado -> {
                            Optional<EstadoModel> estadoModelOptional = estadoService.findOneBySigla(estado);
                            if (estadoModelOptional.isPresent()) {
                                return estadoModelOptional.get();
                            } else {
                                throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("Estado '%s' não cadastrado", estado));
                            }
                        }).collect(Collectors.toList()
                )
        );
        return modelToResponseDto(afinidadeRepository.save(afinidadeModel));
    }

    public Optional<AfinidadeModel> findOneByRegiao(final String regiao){
        return afinidadeRepository.findOneByRegiao(regiao);
    }

    public Boolean existsByRegiao(final String regiao){
        return afinidadeRepository.existsByRegiao(regiao);
    }

    private AfinidadeResponseDto modelToResponseDto(final AfinidadeModel afinidadeModel){
        return AfinidadeResponseDto.builder()
                .regiao(afinidadeModel.getRegiao())
                .estados(afinidadeModel.getEstados().stream().map(EstadoModel::getSigla).collect(Collectors.toList()))
                .build();
    }
}
