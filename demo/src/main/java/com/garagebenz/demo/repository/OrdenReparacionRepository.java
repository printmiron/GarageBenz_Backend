package com.garagebenz.demo.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garagebenz.demo.models.OrdenReparacion;

public interface OrdenReparacionRepository extends JpaRepository<OrdenReparacion, UUID> {

    List<OrdenReparacion> findByVehiculoClienteIdClienteAndEstadoRep(
            String idCliente,
            OrdenReparacion.EstadoRep estadoRep
    );
}
