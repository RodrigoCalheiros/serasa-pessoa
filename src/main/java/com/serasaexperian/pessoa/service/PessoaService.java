package com.serasaexperian.pessoa.service;

import com.serasaexperian.pessoa.dto.request.PessoaRequestDto;
import com.serasaexperian.pessoa.dto.response.PessoaResponseDto;
import com.serasaexperian.pessoa.models.AfinidadeModel;
import com.serasaexperian.pessoa.models.PessoaModel;
import com.serasaexperian.pessoa.models.ScoreModel;
import com.serasaexperian.pessoa.repository.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PessoaService {

    @Autowired
    PessoaRepository pessoaRepository;

    @Autowired
    ScoreService scoreService;

    @Autowired
    AfinidadeService afinidadeService;

    @Transactional
    public PessoaModel save(final PessoaRequestDto pessoaRequestDto){
        PessoaModel pessoaModel = new PessoaModel();
        BeanUtils.copyProperties(pessoaRequestDto, pessoaModel);

        pessoaModel.setDataInclusao(LocalDateTime.now(ZoneId.of("UTC")));

        Optional<ScoreModel> scoreModelOptional = scoreService.findOneByScoreRange(pessoaRequestDto.getScore());
        if (scoreModelOptional.isPresent()) {
            pessoaModel.setScore(scoreModelOptional.get());
        }
        else{
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Score não cadastrado.");
        }

        Optional<AfinidadeModel> afinidadeModelOptional = afinidadeService.findOneByRegiao(pessoaRequestDto.getRegiao());
        if (afinidadeModelOptional.isPresent()){
            pessoaModel.setAfinidade(afinidadeModelOptional.get());
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Região não cadastrada.");
        }

        return pessoaRepository.save(pessoaModel);
    }

    public List<PessoaResponseDto> findAll() {
        List<PessoaModel> allPessoasModel = pessoaRepository.findAll();
        allPessoasModel.forEach(pessoaModel -> {
                pessoaModel.setTelefone(null);
                pessoaModel.setIdade(null);
        });
        return allPessoasModel.stream().map(pessoaModel -> modelToResponseDto(pessoaModel)).collect(Collectors.toList());
    }

    public Optional<PessoaResponseDto> findById(final UUID id) {
        Optional<PessoaModel> pessoaModelOptional = pessoaRepository.findById(id);
        if (pessoaModelOptional.isPresent()){
            pessoaModelOptional.get().setCidade(null);
            pessoaModelOptional.get().setEstado(null);
            return Optional.of(modelToResponseDto(pessoaModelOptional.get()));
        }
        return Optional.empty();
    }

    private PessoaResponseDto modelToResponseDto(PessoaModel pessoaModel){
        return PessoaResponseDto.builder()
                .nome(pessoaModel.getNome())
                .telefone(pessoaModel.getTelefone())
                .idade(pessoaModel.getIdade())
                .cidade(pessoaModel.getCidade())
                .estado(pessoaModel.getEstado())
                .scoreDescricao(pessoaModel.getScore().getDescricao())
                .estados(pessoaModel.getAfinidade().getEstados().stream().map(
                        estado -> estado.getSigla()
                    ).collect(Collectors.toList())
                ).build();
    }

}
