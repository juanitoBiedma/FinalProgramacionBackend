package com.minseg.spring.repository;

import com.minseg.spring.entity.Delito;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DelitoRepository extends JpaRepository<Delito, Long> {
}