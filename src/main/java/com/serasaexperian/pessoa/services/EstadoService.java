package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.models.EstadoModel;
import com.serasaexperian.pessoa.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public EstadoModel findBySigla(String sigla) {
        return estadoRepository.findOneBySigla(sigla);
    }
}
