package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.models.PessoaModel;
import com.serasaexperian.pessoa.repositories.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Transactional
    public PessoaModel save(PessoaModel pessoaModel){
        return pessoaRepository.save(pessoaModel);
    }

}
