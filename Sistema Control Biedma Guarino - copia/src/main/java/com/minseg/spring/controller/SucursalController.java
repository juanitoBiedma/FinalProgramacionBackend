package com.minseg.spring.controller;

import com.minseg.spring.entity.Entidad;
import com.minseg.spring.entity.Sucursal;
import com.minseg.spring.service.EntidadService;
import com.minseg.spring.service.SucursalService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.minseg.spring.utilities.Constantes.*;
import org.springframework.security.access.prepost.PreAuthorize;

@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    @Autowired
    private SucursalService sucursalService;

    @Autowired
    private EntidadService entidadService;

    @GetMapping
    public List<Sucursal> listarSucursales() {
        return sucursalService.obtenerSucursales();
    }

    @GetMapping("/{idSucursal}")
    public Sucursal obtenerSucursalPorId(@PathVariable Long idSucursal) {
        return sucursalService.obtenerSucursalPorId(idSucursal);
    }

    @PostMapping
    public Sucursal crearSucursal(@RequestBody Sucursal sucursal) {

        if (sucursal.getEntidad() != null && sucursal.getEntidad().getIdEntidad() != null) {
            Entidad entidad = entidadService.obtenerEntidadPorId(sucursal.getEntidad().getIdEntidad());
            sucursal.setEntidad(entidad);
        } else {
            throw new IllegalArgumentException(ERROR_ID_ENTIDAD_NOT_FOUND);
        }

        return sucursalService.guardarSucursal(sucursal);
    }

    @PatchMapping("/editarSucursal/{idSucursal}")
    public Sucursal actualizarSucursal(@PathVariable Long idSucursal, @RequestBody Sucursal sucursal) {
        Sucursal sucursalExistente = sucursalService.obtenerSucursalPorId(idSucursal);
        if (sucursalExistente != null) {
            sucursalExistente.setNombreSucursal(sucursal.getNombreSucursal());
            sucursalExistente.setDomicilioSucursal(sucursal.getDomicilioSucursal());
            sucursalExistente.setNEmpleadosSucursal(sucursal.getNEmpleadosSucursal());
            sucursalExistente.setEntidad(sucursal.getEntidad());
            sucursalService.guardarSucursal(sucursalExistente);
        }
        return sucursalExistente;
    }

    @PreAuthorize("hasRole('ADMINISTRADOR')")
    @DeleteMapping("/{idSucursal}")
    public void eliminarSucursal(@PathVariable Long idSucursal) {
        sucursalService.eliminarSucursal(idSucursal);
    }
}
