package com.serasaexperian.pessoa.controller;

import com.serasaexperian.pessoa.dto.request.AfinidadeRequestDto;
import com.serasaexperian.pessoa.dto.response.AfinidadeResponseDto;
import com.serasaexperian.pessoa.service.AfinidadeService;
import com.serasaexperian.pessoa.service.EstadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/afinidade")
public class AfinidadeController {

    final AfinidadeService afinidadeService;

    final EstadoService estadoService;

    public AfinidadeController(final AfinidadeService afinidadeService, final EstadoService estadoService) {
        this.afinidadeService = afinidadeService;
        this.estadoService = estadoService;
    }

    @PostMapping
    public ResponseEntity<AfinidadeResponseDto> saveAfinidade(@RequestBody @Valid final AfinidadeRequestDto afinidadeRequestDto) {
        if (Boolean.TRUE.equals(afinidadeService.existsByRegiao(afinidadeRequestDto.getRegiao()))){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Afinidade com região já cadastrada");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(afinidadeService.save(afinidadeRequestDto));
    }

}
