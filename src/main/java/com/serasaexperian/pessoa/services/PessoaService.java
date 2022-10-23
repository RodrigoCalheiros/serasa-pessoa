package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.dtos.PessoaDto;
import com.serasaexperian.pessoa.models.PessoaModel;
import com.serasaexperian.pessoa.repositories.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Transactional
    public PessoaModel save(PessoaDto pessoaDto){
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaDto, pessoaModel);
        pessoaModel.setDataInclusao(LocalDateTime.now(ZoneId.of("UTC")));
        return pessoaRepository.save(pessoaModel);
    }

    public List<PessoaModel> findAll() {
        return pessoaRepository.findAll();
    }

    public Optional<PessoaModel> findById(UUID id) {
        return pessoaRepository.findById(id);
    }

}
