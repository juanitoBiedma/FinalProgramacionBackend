package com.minseg.spring.repository;

import com.minseg.spring.entity.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUsernameAndPassword(String userUsuario, String passUsuario);

    Optional<Usuario> findUserEntityByUsername(String username);

}
