package com.serasaexperian.pessoa.repositories;

import com.serasaexperian.pessoa.models.AfinidadeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AfinidadeRepository extends JpaRepository<AfinidadeModel, UUID> {
}
