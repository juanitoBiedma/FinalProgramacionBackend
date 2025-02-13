package com.minseg.spring.repository;

import com.minseg.spring.entity.Banda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BandaRepository extends JpaRepository<Banda, Long> {
}