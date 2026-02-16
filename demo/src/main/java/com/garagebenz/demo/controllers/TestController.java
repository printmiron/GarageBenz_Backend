package com.garagebenz.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/seguro")
    public String rutaProtegida() {
        return "Si puedes leer esto, tu JWT funciona perfectamente.";
    }
}