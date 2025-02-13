package com.minseg.spring.controller;

import com.minseg.spring.entity.Delito;
import com.minseg.spring.service.DelitoService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins="http://192.168.1.8:8080")
@RestController
@RequestMapping("/api/delitos")
public class DelitoController {

    @Autowired
    private DelitoService delitoService;

    @GetMapping
    public List<Delito> listarDelitos() {
        return delitoService.obtenerDelitos();
    }

    @GetMapping("/{idDelito}")
    public Delito obtenerDelitoPorId(@PathVariable Long idDelito) {
        return delitoService.obtenerDelitoPorId(idDelito);
    }

    @PostMapping
    public Delito crearDelito(@RequestBody Delito delito) {
        LocalDate fechaDelito = delito.getFechaDelito();
        LocalDate fechaMinima = LocalDate.of(2024, 1, 1);
        LocalDate fechaMaxima = LocalDate.now();

        if (fechaDelito.isBefore(fechaMinima) || fechaDelito.isAfter(fechaMaxima)) {
            throw new IllegalArgumentException("La fecha del delito debe estar entre el 1 de enero de 2024 y la fecha actual.");
        }
        return delitoService.guardarDelito(delito);
    }

    @PatchMapping("/editarDelito/{idDelito}")
    public Delito actualizarDelito(@PathVariable Long idDelito, @RequestBody Delito delito) {
        Delito delitoExistente = delitoService.obtenerDelitoPorId(idDelito);
        if (delitoExistente != null) {
            LocalDate fechaDelito = delito.getFechaDelito();
            LocalDate fechaMinima = LocalDate.of(2024, 1, 1);
            LocalDate fechaMaxima = LocalDate.now();

            if (fechaDelito.isBefore(fechaMinima) || fechaDelito.isAfter(fechaMaxima)) {
                throw new IllegalArgumentException("La fecha del delito debe estar entre el 1 de enero de 2024 y la fecha actual.");
            }

            delitoExistente.setFechaDelito(fechaDelito);
            delitoExistente.setSucursal(delito.getSucursal());
            return delitoService.guardarDelito(delitoExistente);
        }
        throw new IllegalArgumentException("Delito no encontrado");
    }

    @DeleteMapping("/{idDelito}")
    public void eliminarDelito(@PathVariable Long idDelito) {
        delitoService.eliminarDelito(idDelito);
    }
}