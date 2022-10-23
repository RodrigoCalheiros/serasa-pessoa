package com.serasaexperian.pessoa.controllers;

import com.serasaexperian.pessoa.dtos.AfinidadeDto;
import com.serasaexperian.pessoa.services.AfinidadeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/afinidade")
public class AfinidadeController {

    final AfinidadeService afinidadeService;

    public AfinidadeController(AfinidadeService afinidadeService) {
        this.afinidadeService = afinidadeService;
    }

    @PostMapping
    public ResponseEntity<Object> saveAfinidade(@RequestBody @Valid AfinidadeDto afinidadeDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(afinidadeService.save(afinidadeDto));
    }
}
