package com.garagebenz.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.service.IOrdenReparacionService;
import com.garagebenz.demo.service.OrdenReparacionService;

@RestController
@RequestMapping("/ordenes")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdenReparacionController {

    @Autowired
    private IOrdenReparacionService orService;

    @Autowired
    private OrdenReparacionService ordenReparacionService;



    @GetMapping("/historial/{idCliente}")
    public ResponseEntity<List<OrdenReparacion>> getHistorial(@PathVariable String idCliente) {
        // Convertimos el String que viene de Angular a un UUID de Java
        UUID clienteId = UUID.fromString(idCliente);

        List<OrdenReparacion> historial = ordenReparacionService.obtenerHistorialPorCliente(clienteId);
        return ResponseEntity.ok(historial);
    }

    @PostMapping
    public ResponseEntity<OrdenReparacion> crear(@RequestBody OrdenReparacion orden) {
        return ResponseEntity.ok(orService.guardarOrden(orden));
    }
}
