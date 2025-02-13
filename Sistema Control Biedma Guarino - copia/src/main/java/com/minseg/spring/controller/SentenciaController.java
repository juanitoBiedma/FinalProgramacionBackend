package com.minseg.spring.controller;

import com.minseg.spring.entity.Sentencia;
import com.minseg.spring.service.SentenciaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins="http://192.168.1.8:8080")
@RestController
@RequestMapping("/api/sentencias")
public class SentenciaController {

    @Autowired
    private SentenciaService sentenciaService;

    @GetMapping
    public List<Sentencia> listarSentencias() {
        return sentenciaService.obtenerSentencias();
    }

    @GetMapping("/{idSentencia}")
    public Sentencia obtenerSentenciaPorId(@PathVariable Long idSentencia) {
        return sentenciaService.obtenerSentenciaPorId(idSentencia);
    }

    @PostMapping
    public Sentencia crearSentencia(@RequestBody Sentencia sentencia) {
        return sentenciaService.guardarSentencia(sentencia);
    }

    @PatchMapping("/editarSentencia/{idSentencia}")
    public Sentencia actualizarSentencia(@PathVariable Long idSentencia, @RequestBody Sentencia sentencia) {
        Sentencia sentenciaExistente = sentenciaService.obtenerSentenciaPorId(idSentencia);
        if (sentenciaExistente != null) {
            sentenciaExistente.setTiempoSentencia(sentencia.getTiempoSentencia());
            sentenciaExistente.setJuez(sentencia.getJuez());
            sentenciaExistente.setDelito(sentencia.getDelito());
            sentenciaService.guardarSentencia(sentenciaExistente);
        }
        return sentenciaExistente;
    }

    @DeleteMapping("/{idSentencia}")
    public void eliminarSentencia(@PathVariable Long idSentencia) {
        sentenciaService.eliminarSentencia(idSentencia);
    }
}