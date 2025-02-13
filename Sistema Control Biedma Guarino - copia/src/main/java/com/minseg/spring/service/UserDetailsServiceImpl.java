package com.minseg.spring.service;

import com.minseg.spring.entity.Usuario;
import com.minseg.spring.repository.UsuarioRepository;
import java.util.ArrayList;
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

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_".concat(usuario.getRolUsuario().getRolEnum().name()));
        List<SimpleGrantedAuthority> authorityList = Collections.singletonList(authority);

        return new User(
                usuario.getUsername(), // Username
                usuario.getPassword(), // Password
                usuario.isEnabled(), // isEnabled
                usuario.isAccountNoExpired(), // isAccountNonExpired
                usuario.isCredentialNoExpired(), // isCredentialsNonExpired
                usuario.isAccountNoLocked(), // isAccountNonLocked
                authorityList // GrantedAuthorities (roles)
        );
    }
}
