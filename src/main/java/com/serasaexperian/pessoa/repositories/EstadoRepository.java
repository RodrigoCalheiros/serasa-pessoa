package com.serasaexperian.pessoa.repositories;

import com.serasaexperian.pessoa.models.EstadoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EstadoRepository extends JpaRepository<EstadoModel, UUID> {

    Optional<EstadoModel> findOneBySigla(final String sigla);
    List<EstadoModel> findBySiglaIn(final List<String> siglas);
}
