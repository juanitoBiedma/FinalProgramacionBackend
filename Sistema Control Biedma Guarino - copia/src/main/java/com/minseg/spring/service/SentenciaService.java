package com.minseg.spring.service;

import com.minseg.spring.entity.Sentencia;
import com.minseg.spring.repository.SentenciaRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SentenciaService {

    @Autowired
    private SentenciaRepository sentenciaRepository;

    public List<Sentencia> obtenerSentencias() {
        return sentenciaRepository.findAll();
    }

    public Sentencia guardarSentencia(Sentencia sentencia) {
        return sentenciaRepository.save(sentencia);
    }

    public void eliminarSentencia(Long idSentencia) {
        sentenciaRepository.deleteById(idSentencia);
    }

    public Sentencia obtenerSentenciaPorId(Long idSentencia) {
        return sentenciaRepository.findById(idSentencia).orElse(null);
    }
}