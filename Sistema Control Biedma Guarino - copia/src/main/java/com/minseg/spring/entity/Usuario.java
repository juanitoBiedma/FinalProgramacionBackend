package com.minseg.spring.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @Column(nullable = false)
    private String nombreUsuario;

    @Column(nullable = false)
    private String apellidoUsuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    // Atributos para UserDetails de Spring Security
    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "account_no_expired")
    private boolean accountNoExpired;

    @Column(name = "account_no_locked")
    private boolean accountNoLocked;

    @Column(name = "credential_no_expired")
    private boolean credentialNoExpired;

    @ManyToOne
    @JoinColumn(name = "idRol", nullable = false)
    private Rol rolUsuario;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (rolUsuario == null || rolUsuario.getRolEnum() == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + rolUsuario.getRolEnum()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNoExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNoLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialNoExpired;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled;
    }
}
