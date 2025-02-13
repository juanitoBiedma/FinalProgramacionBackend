package com.minseg.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "vigilantes")
public class Vigilante {
    @Id
    @Column(nullable = false)
    private Long idVigilante;
    
    @Column(nullable = false)
    private int edadVigilante;
    
    @Column(nullable = false)
    private boolean estaContratadoVigilante;
    
    @OneToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private Usuario usuario;
}