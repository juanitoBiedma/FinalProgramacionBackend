package com.minseg.spring.repository;

import com.minseg.spring.entity.Sucursal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SucursalRepository extends JpaRepository<Sucursal, Long> {

    List<Sucursal> findByEntidadIdEntidad(Long idEntidad);
}
