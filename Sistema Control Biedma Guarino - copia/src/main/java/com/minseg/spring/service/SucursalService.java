package com.minseg.spring.service;

import com.minseg.spring.entity.Contrato;
import com.minseg.spring.entity.Delito;
import com.minseg.spring.entity.Sucursal;
import com.minseg.spring.repository.SucursalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;

    @Autowired
    private DelitoService delitoService;

    @Autowired
    private ContratoService contratoService;

    public List<Sucursal> obtenerSucursales() {
        return sucursalRepository.findAll();
    }

    public Sucursal guardarSucursal(Sucursal sucursal) {
        return sucursalRepository.save(sucursal);
    }

    public void eliminarSucursal(Long idSucursal) {

        List<Delito> delitos = delitoService.obtenerDelitos();
        List<Contrato> contratos = contratoService.obtenerContratos();

        for (Delito delito : delitos) {
            if (delito.getSucursal().getIdSucursal().equals(idSucursal)) {
                delitoService.eliminarDelito(delito.getIdDelito());
            }
        }

        for (Contrato contrato : contratos) {
            if (contrato.getSucursal().getIdSucursal().equals(idSucursal)) {
                contratoService.eliminarContrato(contrato.getIdContrato());
            }
        }

        sucursalRepository.deleteById(idSucursal);
    }

    public Sucursal obtenerSucursalPorId(Long idSucursal) {
        return sucursalRepository.findById(idSucursal).orElse(null);
    }

    public List<Sucursal> obtenerSucursalesPorEntidad(Long idEntidad) {
        return sucursalRepository.findByEntidadIdEntidad(idEntidad);
    }

}
