package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.repository.OrdenReparacionRepository;

@Service
public class OrdenReparacionService implements IOrdenReparacionService {

    @Autowired
    private OrdenReparacionRepository orRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrdenReparacion> obtenerHistorialPorCliente(UUID idCliente) {
        return orRepository.findByVehiculoClienteIdClienteAndEstadoRep(
            idCliente, 
            OrdenReparacion.EstadoRep.Completada
        );
    }

    @Override
    @Transactional
    public OrdenReparacion guardarOrden(OrdenReparacion orden) {
        return orRepository.save(orden);
    }


    @Override
    @Transactional
    public OrdenReparacion actualizarEstado(UUID idOr, OrdenReparacion.EstadoRep nuevoEstado) {
        OrdenReparacion or = orRepository.findById(idOr)
            .orElseThrow(() -> new RuntimeException("Orden de reparación no encontrada con ID: " + idOr));
        
        or.setEstadoRep(nuevoEstado);
        return orRepository.save(or);
    }
}