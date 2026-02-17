package com.garagebenz.demo.service;

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
        // Buscamos a través de la relación Cliente
        return citaRepository.findByCliente_IdCliente(UUID.fromString(idCliente));
    }

    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    public void updateEstado(String id, String nuevoEstado) {
        // Convertimos el String id a UUID y el String nuevoEstado al Enum
        citaRepository.findById(UUID.fromString(id)).ifPresent(cita -> {
            try {
                cita.setEstado(Cita.EstadoCita.valueOf(nuevoEstado));
                citaRepository.save(cita);
            } catch (IllegalArgumentException e) {
                // Manejar error si el estado enviado no existe en el Enum
                System.out.println("Estado no válido: " + nuevoEstado);
            }
        });
    }
    
    public Optional<Cita> findById(String id) {
        return citaRepository.findById(UUID.fromString(id));
    }
}