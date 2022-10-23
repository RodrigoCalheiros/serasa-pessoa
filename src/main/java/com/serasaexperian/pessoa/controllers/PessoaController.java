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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaService.save(pessoaDto));
    }

    @GetMapping
    public ResponseEntity<List<PessoaModel>> getAllPessoas() {
        List<PessoaModel> allPessoas = pessoaService.findAll();
        if (allPessoas.isEmpty()){
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ArrayList<>());
        }else{
            return ResponseEntity.status(HttpStatus.OK).body(allPessoas);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getPessoa(@PathVariable(value = "id") UUID id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaService.findById(id);
        if (!pessoaModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new PessoaModel());
        }
        return ResponseEntity.status(HttpStatus.OK).body(pessoaModelOptional.get());
    }
}
