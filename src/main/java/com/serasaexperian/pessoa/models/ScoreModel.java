package com.serasaexperian.pessoa.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "tb_score")
@Getter
@Setter
public class ScoreModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid")
    private UUID id;

    @Column(nullable = false)
    private String descricao;

    @Column(name = "inicial", nullable = false)
    private Integer rangeInicial;

    @Column(name = "final", nullable = false)
    private Integer rangeFinal;
}
