package com.minseg.spring.repository;

import com.minseg.spring.entity.Delincuente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DelincuenteRepository extends JpaRepository<Delincuente, Long> {
}