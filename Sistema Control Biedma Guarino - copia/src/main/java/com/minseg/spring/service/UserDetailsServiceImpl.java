package com.minseg.spring.service;

import com.minseg.spring.entity.Usuario;
import com.minseg.spring.repository.UsuarioRepository;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findUserEntityByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + (usuario.getRolUsuario() != null ? usuario.getRolUsuario().getRolEnum().name() : "DEFAULT"));
        List<SimpleGrantedAuthority> authorityList = Collections.singletonList(authority);

        return new User(
                usuario.getUsername(),
                usuario.getPassword(),
                usuario.isEnabled(),
                usuario.isAccountNoExpired(),
                usuario.isCredentialNoExpired(),
                usuario.isAccountNoLocked(),
                authorityList
        );
    }
}
