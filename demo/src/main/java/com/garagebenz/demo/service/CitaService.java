package com.garagebenz.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.models.Cita;
import com.garagebenz.demo.repository.CitaRepository;

@Service
public class CitaService {

    @Autowired
    private CitaRepository citaRepository;

    public List<Cita> findByClienteId(String idCliente) {

        return citaRepository.findByCliente_IdCliente(UUID.fromString(idCliente));
    }

    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    public void updateEstado(String id, String nuevoEstado) {
     
        citaRepository.findById(UUID.fromString(id)).ifPresent(cita -> {
            try {
                cita.setEstado(Cita.EstadoCita.valueOf(nuevoEstado));
                citaRepository.save(cita);
            } catch (IllegalArgumentException e) {
            
                System.out.println("Estado no válido: " + nuevoEstado);
            }
        });
    }

    public Optional<Cita> findById(String id) {
        return citaRepository.findById(UUID.fromString(id));
    }

    public List<Cita> findCitasHoy() {
     
        return citaRepository.findByFechaCitaOrderByHoraCitaAsc(LocalDate.now());
    }
}
