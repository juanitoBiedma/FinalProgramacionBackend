package com.minseg.spring.service;

import com.minseg.spring.entity.Banda;
import com.minseg.spring.repository.BandaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class BandaService {

    @Autowired
    private BandaRepository bandaRepository;

    public List<Banda> obtenerBandas() {
        return bandaRepository.findAll();
    }

    public Banda guardarBanda(Banda banda) {
        return bandaRepository.save(banda);
    }

    public void eliminarBanda(Long idBanda) {
        bandaRepository.deleteById(idBanda);
    }

    public Banda obtenerBandaPorId(Long idBanda) {
        return bandaRepository.findById(idBanda).orElse(null);
    }
}