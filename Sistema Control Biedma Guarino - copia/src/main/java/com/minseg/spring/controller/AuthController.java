package com.minseg.spring.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.minseg.spring.entity.Rol;
import com.minseg.spring.entity.Usuario;
import com.minseg.spring.service.RolService;
import com.minseg.spring.service.UsuarioService;
import static com.minseg.spring.utilities.Constantes.ERROR_ROL_NOT_FOUND;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;

//@CrossOrigin(origins="http://192.168.1.8:8080")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolService rolService;

    @PostMapping("/alta")
    public Usuario altaUsuario(@RequestBody Usuario usuario) {

        // Se verifica si el usuario ya existe
        if (usuarioService.existeUsuarioPorUsername(usuario.getUsername())) {
            throw new RuntimeException("El usuario ya existe");
        }

        // Validar la contraseña
        String password = usuario.getPassword();
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
        if (!password.matches(regex)) {
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial.");
        }

        // Cargar el rol desde la base de datos usando el ID proporcionado en el JSON
        Long rolId = usuario.getRolUsuario().getId();
        Rol rol = rolService.buscarPorId(rolId);

        if (rol == null) {
            throw new RuntimeException(ERROR_ROL_NOT_FOUND);
        }

        usuario.setRolUsuario(rol);
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuario.setEnabled(true);
        usuario.setAccountNoExpired(true);
        usuario.setAccountNoLocked(true);
        usuario.setCredentialNoExpired(true);

        return this.usuarioService.guardarUsuario(usuario);
    }

    // REVISAR SI REALMENTE DEVUELVE LOS STRING ANTES DE LLEVAR A CONSTANTES
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorUsername(username);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            if (passwordEncoder.matches(password, usuario.getPassword())) {
                return "Login exitoso";
            }
        }
        return "Credenciales inválidas";
    }

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
    @GetMapping("/usuario-logueado")
    public ResponseEntity<Usuario> obtenerUsuarioLogueado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        String username = authentication.getName();
        System.out.println("Usuario autenticado: " + username);

        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorUsername(username);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            if (!usuario.isEnabled()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }

            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.notFound().build();
    }
}
