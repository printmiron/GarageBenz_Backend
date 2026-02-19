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

import com.garagebenz.demo.dto.PiezaCrearDTO;
import com.garagebenz.demo.models.Piezas;
import com.garagebenz.demo.service.PiezasService;

@RestController
@RequestMapping("/piezas")
@CrossOrigin(origins = "http://localhost:4200")
public class PiezasController {

    @Autowired
    private PiezasService piezasService;

    // Obtener todas las piezas
    @GetMapping
    public List<Piezas> listar() {
        return piezasService.listarTodas();
    }

    @PostMapping("/crear") 
    public ResponseEntity<Piezas> crearConStock(@RequestBody PiezaCrearDTO dto) {
        Piezas nuevaPieza = piezasService.crearPiezaConStock(dto);
        return new ResponseEntity<>(nuevaPieza, HttpStatus.CREATED);
    }

    
}