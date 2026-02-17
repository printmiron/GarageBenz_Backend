package com.garagebenz.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.OrdenReparacion;

@Repository
public interface OrdenReparacionRepository extends JpaRepository<OrdenReparacion, UUID> {
    
    // Cambia String por UUID aquí
    List<OrdenReparacion> findByVehiculoClienteIdClienteAndEstadoRep(
        UUID idCliente, 
        OrdenReparacion.EstadoRep estadoRep
    );
}
