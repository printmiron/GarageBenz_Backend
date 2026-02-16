package com.garagebenz.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Cita;

@Repository
public interface CitaRepository extends JpaRepository<Cita, UUID> {

    @Query("SELECT c FROM Cita c WHERE c.cliente.idCliente = :idCliente")
    List<Cita> findByClienteId(@Param("idCliente") UUID idCliente);

    List<Cita> findByEstado(Cita.EstadoCita estado);

}
