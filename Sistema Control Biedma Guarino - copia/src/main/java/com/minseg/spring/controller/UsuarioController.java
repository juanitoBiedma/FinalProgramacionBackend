package com.minseg.spring.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.minseg.spring.entity.Contrato;
import com.minseg.spring.entity.Usuario;
import com.minseg.spring.entity.Vigilante;
import com.minseg.spring.service.ContratoService;
import com.minseg.spring.service.UsuarioService;
import com.minseg.spring.service.VigilanteService;
import static com.minseg.spring.utilities.Constantes.ANTIGUA_CONTRASENIA;
import static com.minseg.spring.utilities.Constantes.ERROR_CAMPOS_OBLIGATORIOS;
import static com.minseg.spring.utilities.Constantes.ERROR_OLD_PASS_NO_COINCIDE;
import static com.minseg.spring.utilities.Constantes.ERROR_PASS_NO_COINCIDE;
import static com.minseg.spring.utilities.Constantes.EXITO_CAMBIO_CONTRASENIA;
import static com.minseg.spring.utilities.Constantes.NUEVA_CONTRASENIA;
import static com.minseg.spring.utilities.Constantes.REPETIR_NUEVA_CONTRASENIA;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins="http://192.168.1.8:8080")
@RestController
@RequestMapping({"/api/usuarios"})
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private VigilanteService vigilanteService;

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<Usuario> listarUsuarios() {
        return this.usuarioService.obtenerUsuarios();
    }

    @GetMapping({"/{idUsuario}"})
    public Usuario obtenerUsuarioPorId(@PathVariable Long idUsuario) {
        return this.usuarioService.obtenerUsuarioPorId(idUsuario);
    }

    @PatchMapping({"/editarUsuario/{idUsuario}"})
    public Usuario actualizarUsuario(@PathVariable Long idUsuario, @RequestBody Usuario usuario) {
        Usuario usuarioExistente = this.usuarioService.obtenerUsuarioPorId(idUsuario);
        if (usuarioExistente != null) {
            usuarioExistente.setNombreUsuario(usuario.getNombreUsuario());
            usuarioExistente.setApellidoUsuario(usuario.getApellidoUsuario());
            usuarioExistente.setUsername(usuario.getUsername());
            usuarioExistente.setRolUsuario(usuario.getRolUsuario());
            this.usuarioService.guardarUsuario(usuarioExistente);
        }

        return usuarioExistente;
    }

    @PatchMapping({"/editarContrasenia/{idUsuario}"})
    public ResponseEntity<String> actualizarContrasenia(
            @PathVariable Long idUsuario,
            @RequestBody Map<String, String> contrasenias) {

        if (contrasenias.get(NUEVA_CONTRASENIA) == null
                || contrasenias.get(REPETIR_NUEVA_CONTRASENIA) == null
                || contrasenias.get(ANTIGUA_CONTRASENIA) == null) {
            return ResponseEntity.badRequest().body(ERROR_CAMPOS_OBLIGATORIOS);
        }

        // Extraer las contraseñas del request
        String contraseniaAntigua = contrasenias.get(ANTIGUA_CONTRASENIA);
        String nuevaContrasenia = contrasenias.get(NUEVA_CONTRASENIA);
        String repetirNuevaContrasenia = contrasenias.get(REPETIR_NUEVA_CONTRASENIA);

        // Verificar si las nuevas contraseñas coinciden
        if (!nuevaContrasenia.equals(repetirNuevaContrasenia)) {
            return ResponseEntity.badRequest().body(ERROR_PASS_NO_COINCIDE);
        }

        // Validar la nueva contraseña
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!nuevaContrasenia.matches(regex)) {
            return ResponseEntity.badRequest().body("La nueva contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial.");
        }

        Usuario usuarioExistente = usuarioService.obtenerUsuarioPorId(idUsuario);

        if (passwordEncoder.matches(contraseniaAntigua, usuarioExistente.getPassword())) {
            usuarioExistente.setPassword(passwordEncoder.encode(nuevaContrasenia));
            usuarioService.guardarUsuario(usuarioExistente);
            return ResponseEntity.ok(EXITO_CAMBIO_CONTRASENIA);
        } else {
            return ResponseEntity.badRequest().body(ERROR_OLD_PASS_NO_COINCIDE);
        }
    }

    @DeleteMapping("/eliminarCompleto/{idUsuario}")
    public ResponseEntity<String> eliminarUsuarioCompleto(@PathVariable Long idUsuario) {
        try {
            // Obtener el usuario
            Usuario usuario = usuarioService.obtenerUsuarioPorId(idUsuario);
            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado");
            }

            // Verificar si es un vigilante
            if (usuario.getRolUsuario().getId() == 3) { // Suponiendo que el rol 3 es el de vigilante
                Vigilante vigilante = vigilanteService.obtenerVigilantePorUsuarioId(idUsuario);
                if (vigilante != null) {
                    // Eliminar el contrato del vigilante
                    if (vigilante.isEstaContratadoVigilante()) {
                        Contrato contrato = contratoService.obtenerContratoPorVigilanteId(vigilante.getIdVigilante());
                        if (contrato != null) {
                            contratoService.eliminarContrato(contrato.getIdContrato());
                        }
                    }

                    // Eliminar el vigilante
                    vigilanteService.eliminarVigilante(vigilante.getIdVigilante());
                }
            }

            // Eliminar el usuario
            usuarioService.eliminarUsuario(idUsuario);

            return ResponseEntity.ok("Usuario, vigilante y contrato eliminados exitosamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar usuario y entidades asociadas");
        }
    }

}
