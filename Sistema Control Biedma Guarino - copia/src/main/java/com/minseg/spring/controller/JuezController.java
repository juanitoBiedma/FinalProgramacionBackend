package com.minseg.spring.controller;

import com.minseg.spring.entity.Juez;
import com.minseg.spring.service.JuezService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins="http://192.168.1.8:8080")
@RestController
@RequestMapping("/api/jueces")
public class JuezController {

    @Autowired
    private JuezService juezService;

    @GetMapping
    public List<Juez> listarJueces() {
        return juezService.obtenerJueces();
    }

    @GetMapping("/{idJuez}")
    public Juez obtenerJuezPorId(@PathVariable Long idJuez) {
        return juezService.obtenerJuezPorId(idJuez);
    }

    @PostMapping
    public Juez crearJuez(@RequestBody Juez juez) {
        return juezService.guardarJuez(juez);
    }

    @PatchMapping("/editarJuez/{idJuez}")
    public Juez actualizarJuez(@PathVariable Long idJuez, @RequestBody Juez juez) {
        Juez juezExistente = juezService.obtenerJuezPorId(idJuez);
        if (juezExistente != null) {
            juezExistente.setNombreJuez(juez.getNombreJuez());
            juezExistente.setApellidoJuez(juez.getApellidoJuez());
            juezExistente.setAniosServicioJuez(juez.getAniosServicioJuez());
            juezService.guardarJuez(juezExistente);
        }
        return juezExistente;
    }

    @DeleteMapping("/{idJuez}")
    public void eliminarJuez(@PathVariable Long idJuez) {
        juezService.eliminarJuez(idJuez);
    }
}