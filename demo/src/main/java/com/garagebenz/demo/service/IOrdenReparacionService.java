package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import com.garagebenz.demo.models.OrdenReparacion;

public interface IOrdenReparacionService {
    // Cambia String por UUID aquí también
    List<OrdenReparacion> obtenerHistorialPorCliente(UUID idCliente); 
    
    OrdenReparacion guardarOrden(OrdenReparacion orden);
    OrdenReparacion actualizarEstado(UUID idOr, OrdenReparacion.EstadoRep nuevoEstado);
}