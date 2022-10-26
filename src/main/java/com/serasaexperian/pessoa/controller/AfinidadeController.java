package com.serasaexperian.pessoa.controller;

import com.serasaexperian.pessoa.dto.request.AfinidadeRequestDto;
import com.serasaexperian.pessoa.service.AfinidadeService;
import com.serasaexperian.pessoa.service.EstadoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/afinidade")
public class AfinidadeController {

    private static final Logger LOG = LoggerFactory.getLogger(AfinidadeController.class);

    final AfinidadeService afinidadeService;

    final EstadoService estadoService;

    public AfinidadeController(final AfinidadeService afinidadeService, final EstadoService estadoService) {
        this.afinidadeService = afinidadeService;
        this.estadoService = estadoService;
    }

    @PostMapping
    public ResponseEntity<Object> saveAfinidade(@RequestBody @Valid final AfinidadeRequestDto afinidadeRequestDto) {
        if (afinidadeService.existsByRegiao(afinidadeRequestDto.getRegiao())){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Afinidade com região já cadastrada");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(afinidadeService.save(afinidadeRequestDto));
    }

    private void validateAfinidade(final AfinidadeRequestDto afinidadeRequestDto){

    }
}
