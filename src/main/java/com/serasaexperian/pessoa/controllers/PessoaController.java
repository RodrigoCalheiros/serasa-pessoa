package com.serasaexperian.pessoa.controllers;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.serasaexperian.pessoa.dtos.PessoaDto;
import com.serasaexperian.pessoa.models.PessoaModel;
import com.serasaexperian.pessoa.services.PessoaService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/pessoa")
public class PessoaController {

    final PessoaService pessoaService;

    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping
    public ResponseEntity<Object> savePessoa(@RequestBody @Valid PessoaDto pessoaDto) {
        var pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaDto, pessoaModel);
        pessoaModel.setDataInclusao(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoaModel));
    }
}
