package com.minseg.spring.controller;

import com.minseg.spring.entity.Contrato;
import com.minseg.spring.entity.Vigilante;
import com.minseg.spring.service.ContratoService;
import com.minseg.spring.service.UsuarioService;
import com.minseg.spring.service.VigilanteService;
import java.util.List;
import java.util.Map;
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
@RequestMapping("/api/vigilantes")
public class VigilanteController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VigilanteService vigilanteService;

    @Autowired
    private ContratoService contratoService;

    @GetMapping
    public List<Vigilante> listarVigilantes() {
        return vigilanteService.obtenerVigilantes();
    }

    @GetMapping("/{idVigilante}")
    public Vigilante obtenerVigilantePorId(@PathVariable Long idVigilante) {
        return vigilanteService.obtenerVigilantePorId(idVigilante);
    }

    @PostMapping
    public ResponseEntity<?> crearVigilante(@RequestBody Vigilante vigilante) {
        try {
            // Validar la edad del vigilante
            if (vigilante.getEdadVigilante() < 18 || vigilante.getEdadVigilante() > 65) {
                return new ResponseEntity<>("Ingrese una edad válida (entre 18 y 65 años).", HttpStatus.BAD_REQUEST);
            }

            // Validar el código de vigilante
            if (vigilante.getIdVigilante() < 1) {
                return new ResponseEntity<>("Ingrese un código válido.", HttpStatus.BAD_REQUEST);
            }

            Vigilante nuevoVigilante = vigilanteService.guardarVigilante(vigilante);
            return new ResponseEntity<>(nuevoVigilante, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @PatchMapping("/editarVigilante/{idVigilante}")
    public ResponseEntity<?> actualizarVigilante(@PathVariable Long idVigilante, @RequestBody Vigilante vigilante) {
        Vigilante vigilanteExistente = vigilanteService.obtenerVigilantePorId(idVigilante);

        try {
            // Validar la edad del vigilante
            if (vigilante.getEdadVigilante() < 18 || vigilante.getEdadVigilante() > 65) {
                return new ResponseEntity<>("Ingrese una edad válida (entre 18 y 65 años).", HttpStatus.BAD_REQUEST);
            }

            if (vigilanteExistente != null) {
                vigilanteExistente.setIdVigilante(vigilante.getIdVigilante());
                vigilanteExistente.setEdadVigilante(vigilante.getEdadVigilante());
                vigilanteExistente.setEstaContratadoVigilante(vigilante.isEstaContratadoVigilante());
                vigilanteExistente.setUsuario(vigilante.getUsuario());
                vigilanteService.guardarVigilanteEditado(vigilanteExistente);
            }
            return new ResponseEntity<>(vigilanteExistente, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/eliminarCompleto/{idUsuario}")
    public ResponseEntity<String> eliminarVigilanteCompleto(@PathVariable Long idUsuario) {
        try {
            // Obtener el vigilante por el idUsuario
            Vigilante vigilante = vigilanteService.obtenerVigilantePorUsuarioId(idUsuario);
            if (vigilante == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vigilante no encontrado");
            }

            // Eliminar el contrato del vigilante si existe
            if (vigilante.isEstaContratadoVigilante()) {
                Contrato contrato = contratoService.obtenerContratoPorVigilanteId(vigilante.getIdVigilante());
                if (contrato != null) {
                    contratoService.eliminarContrato(contrato.getIdContrato());
                }
            }

            // Eliminar el vigilante
            vigilanteService.eliminarVigilante(vigilante.getIdVigilante());

            // Finalmente, eliminar el usuario asociado al vigilante
            usuarioService.eliminarUsuario(idUsuario);

            return ResponseEntity.ok("Vigilante, contrato y usuario eliminados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar vigilante y entidades asociadas");
        }
    }

    @GetMapping("/usuario/{idUsuario}")
    public Vigilante obtenerVigilantePorUsuarioId(@PathVariable Long idUsuario) {
        return vigilanteService.obtenerVigilantePorUsuarioId(idUsuario);
    }

    @PostMapping("/{idDelincuente}/asignarDelito")
    public Vigilante asignarContrato(@PathVariable Long idVigilante, @RequestBody Map<String, Long> payload) {
        Long idContrato = payload.get("idContrato");
        Vigilante vigilante = vigilanteService.obtenerVigilantePorId(idVigilante);
        if (vigilante == null) {
            throw new IllegalArgumentException("Vigilante con ID " + idVigilante + " no encontrado");
        }

        Contrato contrato = contratoService.obtenerContratoPorId(idContrato);
        if (contrato == null) {
            throw new IllegalArgumentException("Contrato con ID " + idContrato + " no encontrado");
        }

        // delincuente.getDelitos().add(delito);
        return vigilanteService.guardarVigilante(vigilante);
    }
}
