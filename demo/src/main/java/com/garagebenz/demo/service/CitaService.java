package com.garagebenz.demo.service;



import com.garagebenz.demo.models.Cita;
import com.garagebenz.demo.repository.CitaRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CitaService {

    private final CitaRepository citaRepository;

    
    public CitaService(CitaRepository citaRepository) {
        this.citaRepository = citaRepository;
    }

    // Guardar o actualizar una cita
    public Cita save(Cita cita) {
        return citaRepository.save(cita);
    }

    // Obtener todas las citas
    public List<Cita> findAll() {
        return citaRepository.findAll();
    }

    // Buscar una cita por ID
    public Cita findById(UUID id) {
        return citaRepository.findById(id).orElse(null);
    }

    // Eliminar una cita por ID
    public void deleteById(UUID id) {
        citaRepository.deleteById(id);
    }

    // Ejemplo de búsqueda por cliente
    public List<Cita> findByClienteId(UUID clienteId) {
        return citaRepository.findByClienteId(clienteId);
    }

    // Ejemplo de búsqueda por estado
    public List<Cita> findByEstado(Cita.EstadoCita estado) {
        return citaRepository.findByEstado(estado);
    }

}
