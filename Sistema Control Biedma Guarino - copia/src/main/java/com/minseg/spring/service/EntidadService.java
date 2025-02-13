package com.minseg.spring.service;

import com.minseg.spring.entity.Entidad;
import com.minseg.spring.repository.EntidadRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntidadService {

    @Autowired
    private EntidadRepository entidadRepository;

    public List<Entidad> obtenerEntidades() {
        return entidadRepository.findAll();
    }

    public Entidad guardarEntidad(Entidad entidad) {
        return entidadRepository.save(entidad);
    }

    public void eliminarEntidad(Long idEntidad) {
        entidadRepository.deleteById(idEntidad);
    }

    public Entidad obtenerEntidadPorId(Long idEntidad) {
        return entidadRepository.findById(idEntidad).orElse(null);
    }
}