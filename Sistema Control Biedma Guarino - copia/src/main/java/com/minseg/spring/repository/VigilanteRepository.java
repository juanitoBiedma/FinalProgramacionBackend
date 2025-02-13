package com.minseg.spring.repository;

import com.minseg.spring.entity.Vigilante;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VigilanteRepository extends JpaRepository<Vigilante, Long> {
    Optional<Vigilante> findByUsuario_IdUsuario(Long idUsuario);
}