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
import java.util.List;
import org.springframework.security.config.Customizer;
import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    /*
    private final CorsFilter corsFilter;

    public SecurityConfig(CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
    }
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                /*.cors(httpSecurityCorsConfigurer -> {
                    CorsConfiguration configuration = new CorsConfiguration();
                    configuration.setAllowedOrigins(Arrays.asList("http://192.168.1.8:8080"));
                    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
                    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
                    configuration.setAllowCredentials(true);
                    // configuration.setExposedHeaders(Arrays.asList("*"));
                    configuration.setMaxAge(3600L);
                    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                    source.registerCorsConfiguration("/**", configuration);
                    httpSecurityCorsConfigurer.configurationSource(source);
                })*/
                // Deshabilitar CSRF ya que estamos trabajando con HTML y no con un framework como Thymeleaf
                .csrf(csrf -> csrf.disable()) 
                //.cors(Customizer.withDefaults())
                //.cors(CorsConfigurationSource crs)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                // Permitir el uso de sesiones para manejar el login
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                .authorizeHttpRequests(http -> {
                    http.requestMatchers(INFORMACION_USUARIO).permitAll();
                    http.requestMatchers("http://localhost:3000/login.html", "http://localhost:3000/css/**", "http://localhost:3000/js/**", "http://localhost:3000/img/**").permitAll(); // Permitir acceso a recursos estáticos
                    http.requestMatchers(PERFIL_USUARIO).hasAnyRole(ROL_ADMIN, ROL_INVES, ROL_VIGI);
                    http.requestMatchers(USUARIOS, ENTIDADES, SUCURSALES, VIGILANTES, JUECES, DELITOS, DELINCUENTES, BANDAS, SENTENCIAS, SENTENCIAS_JUEZ, CONTRATOS_VIGILANTE).hasAnyRole(ROL_ADMIN, ROL_INVES);
                    http.requestMatchers(REGISTRAR_USUARIO, EDITAR_USUARIO, EDITAR_CONTRASENIA_USUARIO, REGISTRAR_ENTIDAD, EDITAR_ENTIDAD, REGISTRAR_SUCURSAL, EDITAR_SUCURSAL, REGISTRAR_VIGILANTE, EDITAR_VIGILANTE, REGISTRAR_CONTRATO, REGISTRAR_JUEZ, EDITAR_JUEZ, REGISTRAR_DELITO, EDITAR_DELITO, DELITOS_DELINCUENTE, REGISTRAR_DELINCUENTE, EDITAR_DELINCUENTE, REGISTRAR_BANDA, EDITAR_BANDA, REGISTRAR_SENTENCIA, EDITAR_SENTENCIA).hasRole(ROL_ADMIN);
                    //http.anyRequest().authenticated();
                })
                .authorizeHttpRequests(auth -> auth
                .anyRequest()
                .permitAll())
                // Login personalizado
                .formLogin(form -> form
                .loginPage("http://localhost:3000/login.html") // Página de login customizada
                .loginProcessingUrl(AUTH_LOGIN) //Endpoint que procesa el login
                .defaultSuccessUrl("http://localhost:3000/index.html", true) // Redirige a index tras un login exitoso
                .failureUrl("http://localhost:3000/login.html?error=true") // En caso de error (no hace nada)
                .permitAll()
                )
                // Configuración logout
                .logout(logout -> logout
                .logoutUrl(LOGOUT)
                .logoutSuccessUrl("http://localhost:3000/login.html")
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
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000", "http://localhost:8080", "http://192.168.1.8:8080"));
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(CorsConfiguration.ALL));
        configuration.setMaxAge(1728000L);
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

    /*
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.setAllowedOrigins(List.of("http://192.168.1.8:8080"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
     */
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

    // Configuración del proveedor de autenticación con UserDetailsServiceImpl
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
