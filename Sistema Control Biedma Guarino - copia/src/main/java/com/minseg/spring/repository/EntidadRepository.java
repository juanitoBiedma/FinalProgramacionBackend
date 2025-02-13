package com.minseg.spring.repository;

import com.minseg.spring.entity.Entidad;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EntidadRepository extends JpaRepository<Entidad, Long> {
}