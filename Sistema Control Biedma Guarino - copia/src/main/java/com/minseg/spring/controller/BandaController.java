package com.minseg.spring.controller;

import com.minseg.spring.entity.Banda;
import com.minseg.spring.entity.Delincuente;
import com.minseg.spring.service.BandaService;
import com.minseg.spring.service.DelincuenteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bandas")
public class BandaController {

    @Autowired
    private BandaService bandaService;
    
    @Autowired
    private DelincuenteService delincuenteService;

    @GetMapping
    public List<Banda> listarBandas() {
        return bandaService.obtenerBandas();
    }

    @GetMapping("/{idBanda}")
    public Banda obtenerBandaPorId(@PathVariable Long idBanda) {
        return bandaService.obtenerBandaPorId(idBanda);
    }

    @PostMapping
    public Banda crearBanda(@RequestBody Banda banda) {
        return bandaService.guardarBanda(banda);
    }

    @PatchMapping("/editarBanda/{idBanda}")
    public Banda actualizarBanda(@PathVariable Long idBanda, @RequestBody Banda banda) {
        Banda bandaExistente = bandaService.obtenerBandaPorId(idBanda);
        if (bandaExistente != null) {
            bandaExistente.setNombreBanda(banda.getNombreBanda());
            bandaExistente.setNMiembrosBanda(banda.getNMiembrosBanda());
            bandaService.guardarBanda(bandaExistente);
        }
        return bandaExistente;
    }

    @DeleteMapping("/{idBanda}")
    public void eliminarBanda(@PathVariable Long idBanda) {
        List<Delincuente> delincuentes = delincuenteService.obtenerDelincuentes();
        for(Delincuente delincuente : delincuentes) {
            if(delincuente.getBanda() != null && delincuente.getBanda().getIdBanda().equals(idBanda)) {
                delincuente.setBanda(null);
                delincuenteService.guardarDelincuente(delincuente);
            }
        }
        bandaService.eliminarBanda(idBanda);
    }
}