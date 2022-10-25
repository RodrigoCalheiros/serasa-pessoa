package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.models.EstadoModel;
import com.serasaexperian.pessoa.repositories.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public Optional<EstadoModel> findBySigla(final String sigla) {
        return estadoRepository.findOneBySigla(sigla);
    }

    public List<EstadoModel> findBySiglaIn(final List<String> siglas) {
        return estadoRepository.findBySiglaIn(siglas);
    }
}
