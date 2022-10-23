package com.serasaexperian.pessoa.services;

import com.serasaexperian.pessoa.dtos.request.PessoaRequestDto;
import com.serasaexperian.pessoa.dtos.response.PessoaResponseDto;
import com.serasaexperian.pessoa.models.AfinidadeModel;
import com.serasaexperian.pessoa.models.PessoaModel;
import com.serasaexperian.pessoa.repositories.PessoaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        pessoaModel.setScore(scoreService.findOneByScoreRange(pessoaRequestDto.getScore()));
        pessoaModel.setAfinidade(afinidadeService.findByRegiao(pessoaRequestDto.getRegiao()));

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
