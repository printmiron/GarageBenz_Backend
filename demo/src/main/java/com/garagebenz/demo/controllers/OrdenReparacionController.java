package com.garagebenz.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.service.IOrdenReparacionService;

@RestController
@RequestMapping("/ordenes")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdenReparacionController {

    @Autowired
    private IOrdenReparacionService orService; // Inyectamos la Interfaz del Service

    @GetMapping("/historial/{idCliente}")
    public ResponseEntity<List<OrdenReparacion>> getHistorial(@PathVariable String idCliente) {
        return ResponseEntity.ok(orService.obtenerHistorialPorCliente(idCliente));
    }
}
