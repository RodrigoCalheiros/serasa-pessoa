package com.serasaexperian.pessoa.repository;

import com.serasaexperian.pessoa.model.PessoaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<PessoaModel, UUID>{
}
