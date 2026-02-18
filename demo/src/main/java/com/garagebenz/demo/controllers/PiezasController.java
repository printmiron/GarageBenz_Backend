package com.garagebenz.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.Piezas;
import com.garagebenz.demo.service.IPiezasService;

@RestController
@RequestMapping("/piezas")
@CrossOrigin(origins = "http://localhost:4200")
public class PiezasController {

    @Autowired
    private IPiezasService piezasService;

    // Obtener todas las piezas
    @GetMapping
    public List<Piezas> listar() {
        return piezasService.listarTodas();
    }

    // Crear una nueva pieza (Este es el que usarás en Postman)
    @PostMapping
    public ResponseEntity<Piezas> crear(@RequestBody Piezas pieza) {
        Piezas nuevaPieza = piezasService.guardar(pieza);
        return new ResponseEntity<>(nuevaPieza, HttpStatus.CREATED);
    }

    
}