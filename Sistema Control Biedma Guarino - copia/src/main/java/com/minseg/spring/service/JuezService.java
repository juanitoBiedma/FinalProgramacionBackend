package com.minseg.spring.service;

import com.minseg.spring.entity.Juez;
import com.minseg.spring.repository.JuezRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JuezService {

    @Autowired
    private JuezRepository juezRepository;

    public List<Juez> obtenerJueces() {
        return juezRepository.findAll();
    }

    public Juez guardarJuez(Juez juez) {
        return juezRepository.save(juez);
    }

    public void eliminarJuez(Long idJuez) {
        juezRepository.deleteById(idJuez);
    }

    public Juez obtenerJuezPorId(Long idJuez) {
        return juezRepository.findById(idJuez).orElse(null);
    }
}