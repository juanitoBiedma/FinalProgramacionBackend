package com.minseg.spring.service;

import com.minseg.spring.entity.Delincuente;
import com.minseg.spring.entity.Delito;
import com.minseg.spring.repository.DelincuenteRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelincuenteService {

    @Autowired
    private DelincuenteRepository delincuenteRepository;

    public List<Delincuente> obtenerDelincuentes() {
        return delincuenteRepository.findAll();
    }

    public Delincuente guardarDelincuente(Delincuente delincuente) {
        return delincuenteRepository.save(delincuente);
    }

    public void eliminarDelincuente(Long idDelincuente) {
        delincuenteRepository.deleteById(idDelincuente);
    }

    public Delincuente obtenerDelincuentePorId(Long idDelincuente) {
        return delincuenteRepository.findById(idDelincuente).orElse(null);
    }

    public void desvincularDelito(Long idDelincuente, Long idDelito) {
        Delincuente delincuente = obtenerDelincuentePorId(idDelincuente);
        List<Delito> delitos = delincuente.getDelitos().stream()
                .filter(d -> !d.getIdDelito().equals(idDelito))
                .collect(Collectors.toList());
        delincuente.setDelitos(delitos);
        delincuenteRepository.save(delincuente);
    }
}
