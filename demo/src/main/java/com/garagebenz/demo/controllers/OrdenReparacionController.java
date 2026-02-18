package com.garagebenz.demo.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.service.IOrdenReparacionService;

@RestController
@RequestMapping("/ordenes")
@CrossOrigin(origins = "http://localhost:4200")
public class OrdenReparacionController {

    @Autowired
    private IOrdenReparacionService orService;

   

    @GetMapping("/historial/{idCliente}")
    public ResponseEntity<List<OrdenReparacion>> getHistorial(@PathVariable String idCliente) {
        UUID clienteId = UUID.fromString(idCliente);
        List<OrdenReparacion> historial = orService.obtenerHistorialPorCliente(clienteId);
        return ResponseEntity.ok(historial);
    }

    @PostMapping("/abrir/{idCita}/{idTrabajador}")
    public ResponseEntity<OrdenReparacion> abrirOrden(
            @PathVariable UUID idCita,
            @PathVariable UUID idTrabajador) {
        return ResponseEntity.ok(orService.abrirDesdeCita(idCita, idTrabajador));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<OrdenReparacion>> getActivas() {
        // CAMBIO AQUÍ: Usamos orService y el nombre del método de la interfaz
        return ResponseEntity.ok(orService.obtenerPorEstado(OrdenReparacion.EstadoRep.En_proceso));
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<OrdenReparacion> actualizar(
            @PathVariable UUID id,
            @RequestBody OrdenReparacion ordenData) {
        return ResponseEntity.ok(orService.finalizarOrden(id, ordenData));
    }
}
