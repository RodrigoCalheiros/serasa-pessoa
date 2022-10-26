package com.serasaexperian.pessoa.repository;

import com.serasaexperian.pessoa.model.EstadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoModel, UUID> {

    Optional<EstadoModel> findOneBySigla(final String sigla);
}
