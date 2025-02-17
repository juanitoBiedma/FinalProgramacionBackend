package com.minseg.spring.service;

import com.minseg.spring.entity.Delincuente;
import com.minseg.spring.entity.Delito;
import com.minseg.spring.entity.Sentencia;
import com.minseg.spring.repository.DelincuenteRepository;
import com.minseg.spring.repository.DelitoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DelitoService {

    @Autowired
    private DelitoRepository delitoRepository;

    @Autowired
    private DelincuenteRepository delincuenteRepository;

    @Autowired
    private SentenciaService sentenciaService;

    public List<Delito> obtenerDelitos() {
        return delitoRepository.findAll();
    }

    public Delito obtenerDelitoPorId(Long idDelito) {
        return delitoRepository.findById(idDelito).orElse(null);
    }

    public Delito guardarDelito(Delito delito) {
        return delitoRepository.save(delito);
    }

    public void eliminarDelito(Long idDelito) {
        List<Delincuente> delincuentes = delincuenteRepository.findAll();
        List<Sentencia> sentencias = sentenciaService.obtenerSentencias();

        for (Delincuente delincuente : delincuentes) {
            delincuente.getDelitos().removeIf(delito -> delito.getIdDelito().equals(idDelito));
            delincuenteRepository.save(delincuente);
        }

        for (Sentencia sentencia : sentencias) {
            if (sentencia.getDelito().getIdDelito().equals(idDelito)) {
                sentenciaService.eliminarSentencia(sentencia.getIdSentencia());
            }
        }

        delitoRepository.deleteById(idDelito);
    }
}
