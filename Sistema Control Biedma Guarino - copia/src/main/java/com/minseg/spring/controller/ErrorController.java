package com.minseg.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

//@CrossOrigin(origins="http://192.168.1.8:8080")
@Controller
public class ErrorController {
    
    @GetMapping("/error-403")
    public String error403() {
        return "error-403";
    }
}
