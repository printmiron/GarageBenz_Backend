package com.garagebenz.demo.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, UUID> {

    
    List<Cita> findByCliente_IdCliente(UUID idCliente);

    List<Cita> findByEstado(Cita.EstadoCita estado);

   
    default List<Cita> findByClienteIdString(String idCliente) {
        return findByCliente_IdCliente(UUID.fromString(idCliente));
    }

    default Optional<Cita> findByIdString(String id) {
        return findById(UUID.fromString(id));
    }

    
    List<Cita> findByFechaCitaOrderByHoraCitaAsc(java.time.LocalDate fecha);
}
