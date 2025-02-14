package com.minseg.spring;

import com.minseg.spring.entity.Rol;
import com.minseg.spring.entity.RolEnum;
import com.minseg.spring.entity.Usuario;
import com.minseg.spring.repository.RolRepository;
import com.minseg.spring.repository.UsuarioRepository;
import java.util.List;
import java.util.Set;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.cors.CorsConfiguration;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
    }
/*
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://192.168.1.8:8080")
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .exposedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
            }
        };
    }
    */
    
    @Configuration
    @EnableWebMvc
    public class WebConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowedOriginPatterns("http://localhost:3000", "http://192.168.1.8:8080", "http://localhost:8080")
                    .allowedMethods(CorsConfiguration.ALL)
                    .allowedHeaders(CorsConfiguration.ALL)
                    .maxAge(1728000)
                    .allowCredentials(true);
        }
    }
     /*
    @Bean
    CommandLineRunner init(UsuarioRepository usuarioRepository, RolRepository rolRepository) {
        return args -> {
            // Crear roles
            Rol rolAdmin = Rol.builder()
                    .rolEnum(RolEnum.ADMINISTRADOR)
                    .build();
            Rol rolInves = Rol.builder()
                    .rolEnum(RolEnum.INVESTIGADOR)
                    .build();
            Rol rolVigi = Rol.builder()
                    .rolEnum(RolEnum.VIGILANTE)
                    .build();

            // Guardar los roles en la base de datos primero
            rolRepository.saveAll(List.of(rolAdmin, rolInves, rolVigi));

            // Crear usuarios
            Usuario userFran = Usuario.builder()
                    .nombreUsuario("Francisco")
                    .apellidoUsuario("Guarino Crespo")
                    .username("franciscogua")
                    .password("$2a$10$9eA/O43xlGPno1z2sOy5Q.rg1OvTonZtcIfsglBZAV3id774whw1W") // MinSeg1$
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .rolUsuario(rolAdmin)
                    .build();
            Usuario userJuan = Usuario.builder()
                    .nombreUsuario("Juan Martin")
                    .apellidoUsuario("Biedma")
                    .username("juanbied")
                    .password("$2a$10$E/.wuUag1zVfDSrvCCKceO7g0dlw9hUzv2pqgiBu3NmRsSc5eNfEe") // Bullrich!1
                    .isEnabled(true)
                    .accountNoExpired(true)
                    .accountNoLocked(true)
                    .credentialNoExpired(true)
                    .rolUsuario(rolInves)
                    .build();

            // Guardar los usuarios en la base de datos
            usuarioRepository.saveAll(List.of(userFran, userJuan));
        };
    }
*/
}
