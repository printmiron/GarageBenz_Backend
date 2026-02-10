package com.garagebenz.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, UUID> {
    
    List<Cita> findByClienteId(UUID idCliente);

    List<Cita> findByEstado(Cita.EstadoCita estado);
    
}