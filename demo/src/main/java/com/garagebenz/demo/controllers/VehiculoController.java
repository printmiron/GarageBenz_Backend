package com.garagebenz.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.Vehiculo;
import com.garagebenz.demo.service.VehiculoService;

@RestController
@RequestMapping("/vehiculos")
@CrossOrigin(origins = "http://localhost:4200")
public class VehiculoController {

    @Autowired
    private VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<Vehiculo>> getAll() {
        return ResponseEntity.ok(vehiculoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehiculo> getById(@PathVariable String id) {
        return vehiculoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Vehiculo>> getByCliente(@PathVariable String clienteId) {
        List<Vehiculo> vehiculos = vehiculoService.findByClienteId(clienteId);
        return ResponseEntity.ok(vehiculos);
    }

    @PostMapping
    public Vehiculo create(@RequestBody Vehiculo vehiculo) {
        System.out.println(">>> ENTRANDO AL CONTROLADOR DE VEHICULOS");
        return vehiculoService.save(vehiculo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        vehiculoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
