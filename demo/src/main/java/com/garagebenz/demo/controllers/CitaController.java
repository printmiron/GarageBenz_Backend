package com.garagebenz.demo.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.Cita;
import com.garagebenz.demo.repository.CitaRepository;
import com.garagebenz.demo.service.CitaService;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @Autowired
    private CitaRepository citaRepository;

    @GetMapping("/cliente/{clienteId}")
    public List<Cita> getByCliente(@PathVariable String clienteId) {
        return citaService.findByClienteId(clienteId);
    }

    @PostMapping
    public Cita create(@RequestBody Cita cita) {
        return citaService.save(cita);
    }

    @PatchMapping("/{id}/estado")
    public void updateEstado(@PathVariable String id, @RequestParam String estado) {
        citaService.updateEstado(id, estado);
    }

    @GetMapping("/hoy")
    public ResponseEntity<List<Cita>> getCitasHoy() {
        List<Cita> citas = citaService.findCitasHoy();
        return ResponseEntity.ok(citas);
    }

    // En tu Controller
    @GetMapping("/por-fecha/{fecha}")
    public ResponseEntity<List<Cita>> getCitasPorFecha(@PathVariable String fecha) {
        // Esto aceptará "2026-02-18" desde la URL
        LocalDate date = LocalDate.parse(fecha);
        return ResponseEntity.ok(citaRepository.findByFechaCitaOrderByHoraCitaAsc(date));
    }
}
