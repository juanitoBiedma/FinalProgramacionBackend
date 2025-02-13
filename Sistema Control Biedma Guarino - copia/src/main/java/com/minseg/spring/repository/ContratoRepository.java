package com.minseg.spring.repository;

import com.minseg.spring.entity.Contrato;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {
    Optional<Contrato> findByVigilante_IdVigilante(Long idVigilante);
}