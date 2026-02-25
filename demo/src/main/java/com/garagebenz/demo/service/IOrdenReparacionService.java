package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import com.garagebenz.demo.models.OrdenReparacion;

public interface IOrdenReparacionService {

    List<OrdenReparacion> obtenerHistorialPorCliente(UUID idCliente);

    OrdenReparacion guardarOrden(OrdenReparacion orden);

    OrdenReparacion actualizarEstado(UUID idOr, OrdenReparacion.EstadoRep nuevoEstado);

    OrdenReparacion abrirDesdeCita(UUID idCita, UUID idTrabajador);

    OrdenReparacion finalizarOrden(UUID id, OrdenReparacion datosNuevos);

    List<OrdenReparacion> obtenerPorEstado(OrdenReparacion.EstadoRep estado);
}
