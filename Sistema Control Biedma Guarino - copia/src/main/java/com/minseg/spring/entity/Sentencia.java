package com.minseg.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sentencias")
public class Sentencia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSentencia;
    
    @Column(nullable = false)
    private Long tiempoSentencia;
    
    @ManyToOne
    @JoinColumn(name = "idJuez")
    private Juez juez;
    
    @OneToOne
    @JoinColumn(name = "idDelito")
    private Delito delito;
}