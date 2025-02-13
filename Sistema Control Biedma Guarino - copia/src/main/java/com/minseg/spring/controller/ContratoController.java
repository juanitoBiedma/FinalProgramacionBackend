package com.minseg.spring.controller;

import com.minseg.spring.entity.Contrato;
import com.minseg.spring.entity.Vigilante;
import com.minseg.spring.service.ContratoService;
import com.minseg.spring.service.VigilanteService;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/contratos")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private VigilanteService vigilanteService;

    @GetMapping
    public List<Contrato> listarContratos() {
        return contratoService.obtenerContratos();
    }

    @GetMapping("/{idContrato}")
    public Contrato obtenerContratoPorId(@PathVariable Long idContrato) {
        return contratoService.obtenerContratoPorId(idContrato);
    }

    @PostMapping
    public ResponseEntity<?> crearContrato(@RequestBody Contrato contrato) {
        try {
            LocalDate fechaContrato = contrato.getFechaContrato();
            LocalDate fechaMinima = LocalDate.of(2024, 1, 1);
            LocalDate fechaMaxima = LocalDate.now();

            // Validar la fecha del contrato
            if (fechaContrato.isBefore(fechaMinima) || fechaContrato.isAfter(fechaMaxima)) {
                return new ResponseEntity<>("Fecha de contrato no válida. Debe estar entre 2024-01-01 y la fecha actual.", HttpStatus.BAD_REQUEST);
            }

            Contrato nuevoContrato = contratoService.guardarContrato(contrato);

            return new ResponseEntity<>(nuevoContrato, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al crear el contrato: " + e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/editarContrato/{idContrato}")
    public ResponseEntity<?> actualizarContrato(@PathVariable Long idContrato, @RequestBody Contrato contrato) {
        Contrato contratoExistente = contratoService.obtenerContratoPorId(idContrato);

        try {
            LocalDate fechaContrato = contrato.getFechaContrato();
            LocalDate fechaMinima = LocalDate.of(2024, 1, 1);
            LocalDate fechaMaxima = LocalDate.now();

            // Validar la fecha del contrato
            if (fechaContrato.isBefore(fechaMinima) || fechaContrato.isAfter(fechaMaxima)) {
                return new ResponseEntity<>("Fecha de contrato no válida. Debe estar entre 2024-01-01 y la fecha actual.", HttpStatus.BAD_REQUEST);
            }

            if (contratoExistente != null) {
                contratoExistente.setFechaContrato(contrato.getFechaContrato());
                contratoExistente.setTieneArmaContrato(contrato.isTieneArmaContrato());
                contratoExistente.setSucursal(contrato.getSucursal());
                contratoExistente.setVigilante(contrato.getVigilante());
                contratoService.guardarContrato(contratoExistente);
            }
            return new ResponseEntity<>(contratoExistente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }

    }

    @DeleteMapping("/{idContrato}")
    public void eliminarContrato(@PathVariable Long idContrato) {
        contratoService.eliminarContrato(idContrato);
    }

    @GetMapping("/vigilante/{idVigilante}")
    public Contrato obtenerContratoPorVigilanteId(@PathVariable Long idVigilante) {
        return contratoService.obtenerContratoPorVigilanteId(idVigilante);
    }
}
