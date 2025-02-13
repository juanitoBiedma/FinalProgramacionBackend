package com.minseg.spring.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bandas")
public class Banda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBanda;

    @Column(nullable = false)
    private String nombreBanda;

    @JsonProperty("nMiembrosBanda")
    @Column(nullable = false)
    private int nMiembrosBanda;
}
