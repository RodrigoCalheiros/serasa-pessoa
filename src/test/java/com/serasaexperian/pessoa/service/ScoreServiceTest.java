package com.serasaexperian.pessoa.service;


import com.serasaexperian.pessoa.dto.request.ScoreRequestDto;
import com.serasaexperian.pessoa.dto.response.ScoreResponseDto;
import com.serasaexperian.pessoa.model.ScoreModel;
import com.serasaexperian.pessoa.repository.ScoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ScoreServiceTest {
    @Mock
    private ScoreRepository repository;
    @InjectMocks
    private ScoreService scoreService;

    private ScoreModel scoreModel;

    private UUID id = UUID.randomUUID();
    private String descricao = "Recomendável";
    private Integer rangeInicial = 701;
    private Integer rangeFinal = 1000;
    private Integer score = 800;

    @BeforeEach
    void setUp()
    {
        this.scoreModel = new ScoreModel();
        this.scoreModel.setId(this.id);
        this.scoreModel.setDescricao(this.descricao);
        this.scoreModel.setRangeInicial(this.rangeInicial);
        this.scoreModel.setRangeFinal(this.rangeFinal);
    }

    @Test
    void saveSucesso() {
        when(repository.save(any(ScoreModel.class))).thenReturn(this.scoreModel);

        ScoreResponseDto scoreResponseDto = this.scoreService.save(
                ScoreRequestDto.builder()
                .descricao(this.descricao)
                .rangeInicial(this.rangeInicial)
                .rangeFinal(this.rangeFinal)
                .build());

        assertEquals(scoreResponseDto.getScoreDescricao(), this.descricao);
        assertEquals(scoreResponseDto.getRangeInicial(), this.rangeInicial);
        assertEquals(scoreResponseDto.getRangeFinal(), this.rangeFinal);
    }


    @Test
    void findOneByScoreRangeSucesso() {
        when(repository.findOneByScoreRange(anyInt())).thenReturn(Optional.of(this.scoreModel));

        Optional<ScoreModel> scoreModelOptional = scoreService.findOneByScoreRange(this.score);

        assertTrue(scoreModelOptional.isPresent());
        assertEquals(scoreModelOptional.get().getId(), this.id);
        assertEquals(scoreModelOptional.get().getDescricao(), this.descricao);
        assertEquals(scoreModelOptional.get().getRangeInicial(), this.rangeInicial);
        assertEquals(scoreModelOptional.get().getRangeFinal(), this.rangeFinal);
    }
}
