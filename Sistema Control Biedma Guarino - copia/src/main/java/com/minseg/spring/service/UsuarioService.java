package com.minseg.spring.service;

import com.minseg.spring.entity.Usuario;
import com.minseg.spring.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    public List<Usuario> obtenerUsuarios() {
        return this.usuarioRepository.findAll();
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return (Usuario) this.usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long idUsuario) {
        this.usuarioRepository.deleteById(idUsuario);
    }

    public Usuario obtenerUsuarioPorId(Long idUsuario) {
        return (Usuario) this.usuarioRepository.findById(idUsuario).orElse(null);
    }

    public boolean verificarCredencialesUsuario(Usuario usuario) {
        return usuarioRepository.findByUsernameAndPassword(usuario.getUsername(), usuario.getPassword()).isPresent();
    }

    public Optional<Usuario> buscarUsuarioPorUsername(String username) {
        return usuarioRepository.findUserEntityByUsername(username);
    }

    public boolean existeUsuarioPorUsername(String username) {
        return usuarioRepository.findUserEntityByUsername(username).isPresent();
    }
}