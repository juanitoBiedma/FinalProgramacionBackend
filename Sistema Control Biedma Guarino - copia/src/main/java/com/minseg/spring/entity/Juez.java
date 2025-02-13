package com.minseg.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "jueces")
public class Juez {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idJuez;
    
    @Column(nullable = false)
    private String nombreJuez;
    
    @Column(nullable = false)
    private String apellidoJuez;
    
    @Column(nullable = false)
    private int aniosServicioJuez;
}