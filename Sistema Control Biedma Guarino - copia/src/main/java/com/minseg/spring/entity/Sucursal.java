package com.minseg.spring.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "sucursales")
public class Sucursal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSucursal;

    @Column(nullable = false)
    private String nombreSucursal;

    @Column(nullable = false)
    private String domicilioSucursal;

    @JsonProperty("nEmpleadosSucursal")
    @Column(nullable = false)
    private int nEmpleadosSucursal;

    @ManyToOne
    @JoinColumn(name = "idEntidad", nullable = false)
    private Entidad entidad;
}
