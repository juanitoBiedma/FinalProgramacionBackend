package com.minseg.spring.controller;

import com.minseg.spring.entity.Entidad;
import com.minseg.spring.entity.Sucursal;
import com.minseg.spring.service.EntidadService;
import com.minseg.spring.service.SucursalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/entidades")
public class EntidadController {

    @Autowired
    private EntidadService entidadService;

    @Autowired
    private SucursalService sucursalService;

    @GetMapping
    public List<Entidad> listarEntidades() {
        return entidadService.obtenerEntidades();
    }

    @GetMapping("/{idEntidad}")
    public Entidad obtenerEntidadPorId(@PathVariable Long idEntidad) {
        return entidadService.obtenerEntidadPorId(idEntidad);
    }

    @PostMapping
    public Entidad crearEntidad(@RequestBody Entidad entidad) {
        return entidadService.guardarEntidad(entidad);
    }

    @PatchMapping("/editarEntidad/{idEntidad}")
    public Entidad actualizarEntidad(@PathVariable Long idEntidad, @RequestBody Entidad entidad) {
        Entidad entidadExistente = entidadService.obtenerEntidadPorId(idEntidad);
        if (entidadExistente != null) {
            entidadExistente.setNombreEntidad(entidad.getNombreEntidad());
            entidadExistente.setDomicilioEntidad(entidad.getDomicilioEntidad());
            entidadService.guardarEntidad(entidadExistente);
        }
        return entidadExistente;
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{idEntidad}")
    public void eliminarEntidad(@PathVariable Long idEntidad) {
        List<Sucursal> sucursales = sucursalService.obtenerSucursalesPorEntidad(idEntidad);
        for (Sucursal sucursal : sucursales) {
            sucursalService.eliminarSucursal(sucursal.getIdSucursal());
        }
        entidadService.eliminarEntidad(idEntidad);
    }
}
