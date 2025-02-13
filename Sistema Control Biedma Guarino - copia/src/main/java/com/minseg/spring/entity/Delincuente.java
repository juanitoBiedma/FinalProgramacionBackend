package com.minseg.spring.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "delincuentes")
public class Delincuente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDelincuente;

    @Column(nullable = false)
    private String nombreDelincuente;

    @Column(nullable = false)
    private String apellidoDelincuente;
    
    @ManyToOne
    @JoinColumn(name = "idBanda", nullable = true)
    private Banda banda;

    @ManyToMany
    @JoinTable(name = "delincuente_delito",
            joinColumns = @JoinColumn(name = "idDelincuente"),
            inverseJoinColumns = @JoinColumn(name = "idDelito"))
    private List<Delito> delitos;
}
