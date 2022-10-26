package com.serasaexperian.pessoa.service;

import com.serasaexperian.pessoa.model.EstadoModel;
import com.serasaexperian.pessoa.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public Optional<EstadoModel> findOneBySigla(final String sigla) {
        return estadoRepository.findOneBySigla(sigla);
    }

}
