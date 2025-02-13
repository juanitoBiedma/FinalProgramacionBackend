package com.minseg.spring.service;

import com.minseg.spring.entity.Vigilante;
import com.minseg.spring.repository.VigilanteRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class VigilanteService {

    @Autowired
    private VigilanteRepository vigilanteRepository;

    public List<Vigilante> obtenerVigilantes() {
        return vigilanteRepository.findAll();
    }

    public Vigilante guardarVigilante(Vigilante vigilante) {
        if (vigilanteRepository.existsById(vigilante.getIdVigilante())) {
            throw new IllegalArgumentException("El código de vigilante ya existe.");
        }
        return vigilanteRepository.save(vigilante);
    }
    
    public Vigilante guardarVigilanteEditado(Vigilante vigilante) {
        return vigilanteRepository.save(vigilante);
    }

    public void eliminarVigilante(Long idVigilante) {
        vigilanteRepository.deleteById(idVigilante);
    }

    public Vigilante obtenerVigilantePorId(Long idVigilante) {
        return vigilanteRepository.findById(idVigilante).orElse(null);
    }
    
    public Vigilante obtenerVigilantePorUsuarioId(Long idUsuario) {
        return vigilanteRepository.findByUsuario_IdUsuario(idUsuario).orElse(null);
    }
}