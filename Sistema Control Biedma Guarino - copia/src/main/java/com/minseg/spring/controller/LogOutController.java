/*package com.minseg.spring.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogOutController {

    @CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://192.168.1.8:8080"})
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("\n---ESTAMOS EN LOGOUT---\n");
        // Invalida la sesión actual si existe
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        // Elimina la cookie JSESSIONID
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok().build();
    }
}*/