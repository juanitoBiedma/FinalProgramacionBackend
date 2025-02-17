package com.minseg.spring.controller;

import com.minseg.spring.entity.Banda;
import com.minseg.spring.entity.Delincuente;
import com.minseg.spring.entity.Delito;
import com.minseg.spring.service.BandaService;
import com.minseg.spring.service.DelincuenteService;
import com.minseg.spring.service.DelitoService;
import static com.minseg.spring.utilities.Constantes.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delincuentes")
public class DelincuenteController {

    @Autowired
    private DelincuenteService delincuenteService;

    @Autowired
    private BandaService bandaService;

    @Autowired
    private DelitoService delitoService;

    @GetMapping
    public List<Delincuente> listarDelincuentes() {
        return delincuenteService.obtenerDelincuentes();
    }

    @GetMapping("/{idDelincuente}")
    public Delincuente obtenerDelincuentePorId(@PathVariable Long idDelincuente) {
        return delincuenteService.obtenerDelincuentePorId(idDelincuente);
    }

    @PostMapping
    public Delincuente crearDelincuente(@RequestBody Delincuente delincuente) {

        if (delincuente.getBanda() != null && delincuente.getBanda().getIdBanda() != null) {
            Banda banda = bandaService.obtenerBandaPorId(delincuente.getBanda().getIdBanda());
            if (banda == null) {
                throw new IllegalArgumentException(ERROR_ID_BANDA_NOT_FOUND);
            }
            delincuente.setBanda(banda);
            banda.setNMiembrosBanda(banda.getNMiembrosBanda() + 1);
            bandaService.guardarBanda(banda);

        } else {
            delincuente.setBanda(null);
        }

        List<Delito> delitos = delincuente.getDelitos();
        if (delitos != null && !delitos.isEmpty()) {
            List<Delito> delitosExistentes = new ArrayList<>();

            for (Delito delito : delitos) {
                Delito delitoExistente = delitoService.obtenerDelitoPorId(delito.getIdDelito());
                if (delitoExistente == null) {
                    throw new IllegalArgumentException("Delito con ID " + delito.getIdDelito() + " no encontrado");
                }
                delitosExistentes.add(delitoExistente);
            }
            delincuente.setDelitos(delitosExistentes);
        }

        return delincuenteService.guardarDelincuente(delincuente);
    }

    @PatchMapping("/editarDelincuente/{idDelincuente}")
    public Delincuente actualizarDelincuente(@PathVariable Long idDelincuente, @RequestBody Delincuente delincuente) {
        Delincuente delincuenteExistente = delincuenteService.obtenerDelincuentePorId(idDelincuente);
        if (delincuenteExistente != null) {
            Banda bandaAnterior = delincuenteExistente.getBanda();
            Banda nuevaBanda = delincuente.getBanda();

            delincuenteExistente.setNombreDelincuente(delincuente.getNombreDelincuente());
            delincuenteExistente.setApellidoDelincuente(delincuente.getApellidoDelincuente());

            List<Delito> delitos = delincuente.getDelitos();
            if (delitos != null && !delitos.isEmpty()) {
                List<Delito> delitosExistentes = new ArrayList<>();
                for (Delito delito : delitos) {
                    Delito delitoExistente = delitoService.obtenerDelitoPorId(delito.getIdDelito());
                    if (delitoExistente == null) {
                        throw new IllegalArgumentException("Delito con ID " + delito.getIdDelito() + " no encontrado");
                    }
                    delitosExistentes.add(delitoExistente);
                }
                delincuenteExistente.setDelitos(delitosExistentes);
            } else {
                delincuenteExistente.setDelitos(new ArrayList<>());
            }

            if (bandaAnterior != null && (nuevaBanda == null || !bandaAnterior.equals(nuevaBanda))) {
                bandaAnterior.setNMiembrosBanda(bandaAnterior.getNMiembrosBanda() - 1);
                bandaService.guardarBanda(bandaAnterior);
            }

            if (nuevaBanda != null && (bandaAnterior == null || !nuevaBanda.equals(bandaAnterior))) {
                Banda banda = bandaService.obtenerBandaPorId(nuevaBanda.getIdBanda());
                if (banda == null) {
                    throw new IllegalArgumentException(ERROR_ID_BANDA_NOT_FOUND);
                }
                banda.setNMiembrosBanda(banda.getNMiembrosBanda() + 1);
                bandaService.guardarBanda(banda);
                delincuenteExistente.setBanda(banda);
            } else {
                delincuenteExistente.setBanda(null);
            }

            delincuenteService.guardarDelincuente(delincuenteExistente);
        }
        return delincuenteExistente;
    }

    @DeleteMapping("/{idDelincuente}")
    public void eliminarDelincuente(@PathVariable Long idDelincuente) {
        Delincuente delincuente = delincuenteService.obtenerDelincuentePorId(idDelincuente);
        if (delincuente != null && delincuente.getBanda() != null) {
            Banda banda = delincuente.getBanda();
            banda.setNMiembrosBanda(banda.getNMiembrosBanda() - 1);
            bandaService.guardarBanda(banda);
        }
        delincuenteService.eliminarDelincuente(idDelincuente);
    }
    
    @PostMapping("/{idDelincuente}/asignarDelito")
    public Delincuente asignarDelito(@PathVariable Long idDelincuente, @RequestBody Map<String, Long> payload) {
        Long idDelito = payload.get("idDelito");
        Delincuente delincuente = delincuenteService.obtenerDelincuentePorId(idDelincuente);
        if (delincuente == null) {
            throw new IllegalArgumentException("Delincuente con ID " + idDelincuente + " no encontrado");
        }

        Delito delito = delitoService.obtenerDelitoPorId(idDelito);
        if (delito == null) {
            throw new IllegalArgumentException("Delito con ID " + idDelito + " no encontrado");
        }

        delincuente.getDelitos().add(delito);
        return delincuenteService.guardarDelincuente(delincuente);
    }
    
    @DeleteMapping("/{idDelincuente}/{idDelito}")
    public void desvincularDelitoDelincuente(@PathVariable Long idDelincuente, @PathVariable Long idDelito) {
        delincuenteService.desvincularDelito(idDelincuente, idDelito);
    }
}
