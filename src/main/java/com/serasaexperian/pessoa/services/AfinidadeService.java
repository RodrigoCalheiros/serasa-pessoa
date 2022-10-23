package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.dtos.AfinidadeDto;
import com.serasaexperian.pessoa.models.AfinidadeModel;
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

    public AfinidadeModel save(AfinidadeDto afinidadeDto) {
        var afinidadeModel = new AfinidadeModel();
        BeanUtils.copyProperties(afinidadeDto, afinidadeModel);
        afinidadeModel.getEstados().addAll(
                afinidadeDto.getEstados().stream().map(
                        estado -> estadoService.findBySigla(estado)).collect(Collectors.toList()
                )
        );
        return afinidadeRepository.save(afinidadeModel);
    }
}
