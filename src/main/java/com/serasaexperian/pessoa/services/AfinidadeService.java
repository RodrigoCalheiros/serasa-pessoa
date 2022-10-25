package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.dtos.request.AfinidadeRequestDto;
import com.serasaexperian.pessoa.dtos.response.AfinidadeResponseDto;
import com.serasaexperian.pessoa.models.AfinidadeModel;
import com.serasaexperian.pessoa.models.EstadoModel;
import com.serasaexperian.pessoa.repositories.AfinidadeRepository;
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

    public AfinidadeResponseDto save(final AfinidadeRequestDto afinidadeRequestDto) {
        AfinidadeModel afinidadeModel = new AfinidadeModel();
        BeanUtils.copyProperties(afinidadeRequestDto, afinidadeModel);
        afinidadeModel.getEstados().addAll(
                afinidadeRequestDto.getEstados().stream().map( estado -> {
                            Optional<EstadoModel> estadoModelOptional = estadoService.findBySigla(estado);
                            if (estadoModelOptional.isPresent()) {
                                return estadoModelOptional.get();
                            } else {
                                throw new ResponseStatusException(HttpStatus.CONFLICT, "Estado não cadastrado");
                            }
                        }).collect(Collectors.toList()
                )
        );
        return modelToResponseDto(afinidadeRepository.save(afinidadeModel));
    }

    public Optional<AfinidadeModel> findByRegiao(final String regiao){
        return afinidadeRepository.findOneByRegiao(regiao);
    }

    public Boolean existsByRegiao(final String regiao){
        return afinidadeRepository.existsByRegiao(regiao);
    }

    private AfinidadeResponseDto modelToResponseDto(final AfinidadeModel afinidadeModel){
        return AfinidadeResponseDto.builder()
                .regiao(afinidadeModel.getRegiao())
                .estados(afinidadeModel.getEstados().stream().map(estado -> estado.getSigla()).collect(Collectors.toList()))
                .build();
    }
}
