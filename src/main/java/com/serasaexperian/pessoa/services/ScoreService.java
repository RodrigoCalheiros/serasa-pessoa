package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.dtos.request.ScoreRequestDto;
import com.serasaexperian.pessoa.dtos.response.ScoreResponseDto;
import com.serasaexperian.pessoa.map.ScoreMapper;
import com.serasaexperian.pessoa.models.ScoreModel;
import com.serasaexperian.pessoa.repositories.ScoreRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreService {

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    ScoreMapper scoreMapper;

    public ScoreResponseDto save(ScoreRequestDto scoreRequestDto){
        var scoreModel = new ScoreModel();
        BeanUtils.copyProperties(scoreRequestDto, scoreModel);
        return scoreMapper.modelToResponseDto(scoreRepository.save(scoreModel));
    }
}
