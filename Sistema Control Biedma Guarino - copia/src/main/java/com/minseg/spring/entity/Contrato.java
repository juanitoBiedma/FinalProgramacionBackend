package com.minseg.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDate;
import lombok.Data;

@Data
@Entity
@Table(name = "contratos")
public class Contrato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idContrato;
    
    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate fechaContrato;
        
    @Column(nullable = false)
    private boolean tieneArmaContrato;
    
    @ManyToOne
    @JoinColumn(name = "idVigilante", nullable = false)
    private Vigilante vigilante;
    
    @ManyToOne
    @JoinColumn(name = "idSucursal", nullable = false)
    private Sucursal sucursal;
}
