package com.minseg.spring.service;

import com.minseg.spring.entity.Contrato;
import com.minseg.spring.repository.ContratoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    public List<Contrato> obtenerContratos() {
        return contratoRepository.findAll();
    }

    public Contrato guardarContrato(Contrato contrato) {
        return contratoRepository.save(contrato);
    }

    public void eliminarContrato(Long idContrato) {
        contratoRepository.deleteById(idContrato);
    }

    public Contrato obtenerContratoPorId(Long idContrato) {
        return contratoRepository.findById(idContrato).orElse(null);
    }
    
    public Contrato obtenerContratoPorVigilanteId(Long idVigilante) {
        return contratoRepository.findByVigilante_IdVigilante(idVigilante).orElse(null);
    }
}