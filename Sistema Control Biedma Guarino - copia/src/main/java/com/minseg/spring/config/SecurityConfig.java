package com.minseg.spring.config;

import com.minseg.spring.service.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static com.minseg.spring.utilities.Constantes.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                // Deshabilitar CSRF ya que estamos trabajando con HTML y no con un framework como Thymeleaf
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Permitir el uso de sesiones para manejar el login
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(INFORMACION_USUARIO, LOGOUT, AUTH_LOGIN, AUTH, AUTH_ALTA).permitAll();
                    http.requestMatchers("https://192.168.1.9:3000/public/login.html", "https://192.168.1.9:3000/public/css/**", "https://192.168.1.9:3000/public/js/**", "https://192.168.1.9:3000/public/img/**").permitAll(); // Permitir acceso a recursos estáticos
                    http.requestMatchers(PERFIL_USUARIO).hasAnyRole(ROL_ADMIN, ROL_INVES, ROL_VIGI);
                    http.requestMatchers(USUARIOS, ENTIDADES, SUCURSALES, VIGILANTES, JUECES, DELITOS, DELINCUENTES, BANDAS, SENTENCIAS, SENTENCIAS_JUEZ, CONTRATOS_VIGILANTE).hasAnyRole(ROL_ADMIN, ROL_INVES);
                    http.requestMatchers(REGISTRAR_USUARIO, EDITAR_USUARIO, EDITAR_CONTRASENIA_USUARIO, REGISTRAR_ENTIDAD, EDITAR_ENTIDAD, REGISTRAR_SUCURSAL, EDITAR_SUCURSAL, REGISTRAR_VIGILANTE, EDITAR_VIGILANTE, REGISTRAR_CONTRATO, REGISTRAR_JUEZ, EDITAR_JUEZ, REGISTRAR_DELITO, EDITAR_DELITO, DELITOS_DELINCUENTE, REGISTRAR_DELINCUENTE, EDITAR_DELINCUENTE, REGISTRAR_BANDA, EDITAR_BANDA, REGISTRAR_SENTENCIA, EDITAR_SENTENCIA).hasRole(ROL_ADMIN);
                    http.anyRequest().authenticated();
                })
                //Manejo de Logout
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.Authentication authentication) -> {
                            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
                                response.setStatus(HttpServletResponse.SC_OK);
                            } else {
                                response.sendRedirect("https://192.168.1.9:3000/public/login.html");
                            }
                        })
                        .permitAll()
                )
                .exceptionHandling(exception -> exception
                .accessDeniedPage(FORBIDDEN_403)
                )
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("https://192.168.1.9:3000"));
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        configuration.setMaxAge(1728000L);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
    
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.sendRedirect(INDEX);
            }
        };
    }

    // Gestión de autenticación
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuración del proveedor de autenticación
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    // Para encriptar las contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    // Para agregar en el CommandLineRunner
    public static void main(String[] args) {
        String contraseña = "";
        System.out.println("Contrasenia encriptada: ");
        System.out.println(new BCryptPasswordEncoder().encode(contraseña));
    }
     */
}
