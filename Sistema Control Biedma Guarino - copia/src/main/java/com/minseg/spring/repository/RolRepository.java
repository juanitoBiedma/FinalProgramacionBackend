package com.minseg.spring.repository;

import com.minseg.spring.entity.Rol;
import com.minseg.spring.entity.RolEnum;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByRolEnum(RolEnum rolEnum);
}
