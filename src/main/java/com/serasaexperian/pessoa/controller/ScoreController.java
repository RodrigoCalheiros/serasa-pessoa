package com.serasaexperian.pessoa.controller;

import com.serasaexperian.pessoa.dto.request.ScoreRequestDto;
import com.serasaexperian.pessoa.dto.response.ScoreResponseDto;
import com.serasaexperian.pessoa.service.ScoreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    final ScoreService scoreService;

    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @PostMapping
    public ResponseEntity<ScoreResponseDto> saveScore(@RequestBody @Valid final ScoreRequestDto scoreRequestDto) {
        if (scoreRequestDto.getRangeInicial() >= scoreRequestDto.getRangeFinal()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Valor inicial deve ser menor que o final");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(scoreService.save(scoreRequestDto));
    }

}
