package com.serasaexperian.pessoa.service;

import com.serasaexperian.pessoa.dto.request.ScoreRequestDto;
import com.serasaexperian.pessoa.dto.response.ScoreResponseDto;
import com.serasaexperian.pessoa.model.ScoreModel;
import com.serasaexperian.pessoa.repository.ScoreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    ScoreRepository scoreRepository;

    public ScoreResponseDto save(final ScoreRequestDto scoreRequestDto){
        var scoreModel = new ScoreModel();
        BeanUtils.copyProperties(scoreRequestDto, scoreModel);
        return modelToResponseDto(scoreRepository.save(scoreModel));
    }

    public Optional<ScoreModel> findOneByScoreRange(final Integer score){
        return scoreRepository.findOneByScoreRange(score);
    }

    private ScoreResponseDto modelToResponseDto(final ScoreModel scoreModel){
        return ScoreResponseDto.builder()
                .scoreDescricao(scoreModel.getDescricao())
                .rangeInicial(scoreModel.getRangeInicial())
                .rangeFinal(scoreModel.getRangeFinal())
                .build();
    }
}
