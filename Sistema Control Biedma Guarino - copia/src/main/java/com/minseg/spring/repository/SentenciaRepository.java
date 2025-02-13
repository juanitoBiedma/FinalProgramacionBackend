package com.minseg.spring.repository;

import com.minseg.spring.entity.Sentencia;
import org.springframework.data.jpa.repository.JpaRepository;


public interface SentenciaRepository extends JpaRepository<Sentencia, Long> {
}