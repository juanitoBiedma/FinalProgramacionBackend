package com.minseg.spring.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins="http://192.168.1.8:8080")
@RestController
public class LogOutController {
    
    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        System.out.println("\n---ESTAMOS EN LOGOUT---\n");
        Cookie cookie = new Cookie("JSESSIONID", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
