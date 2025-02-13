package com.minseg.spring.service;

import com.minseg.spring.entity.Rol;
import com.minseg.spring.entity.RolEnum;
import com.minseg.spring.repository.RolRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RolService {

    @Autowired
    private RolRepository rolRepository;

    public Optional<Rol> buscarPorRolEnum(RolEnum rolEnum) {
        return rolRepository.findByRolEnum(rolEnum);
    }

    public Rol buscarPorId(Long idRol) {
        return (Rol) this.rolRepository.findById(idRol).orElse(null);
    }
}
