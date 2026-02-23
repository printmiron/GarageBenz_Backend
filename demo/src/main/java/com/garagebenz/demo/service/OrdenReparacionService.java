package com.garagebenz.demo.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.garagebenz.demo.models.Cita;
import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.models.Trabajador;
import com.garagebenz.demo.repository.CitaRepository;
import com.garagebenz.demo.repository.OrdenReparacionRepository;
import com.garagebenz.demo.repository.TrabajadorRepository;

@Service
public class OrdenReparacionService implements IOrdenReparacionService {

    @Autowired
    private OrdenReparacionRepository orRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private TrabajadorRepository trabajadorRepository;

    @Override
    @Transactional(readOnly = true)
    public List<OrdenReparacion> obtenerHistorialPorCliente(UUID idCliente) {
        return orRepository.findByVehiculoClienteIdClienteAndEstadoRep(
                idCliente,
                OrdenReparacion.EstadoRep.Completada
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrdenReparacion> obtenerPorEstado(OrdenReparacion.EstadoRep estado) {
        return orRepository.findByEstadoRep(estado);
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
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + idOr));
        or.setEstadoRep(nuevoEstado);
        return orRepository.save(or);
    }

    /**
     * PASO 1: Abrir la orden desde la agenda. La Cita pasa a 'En_proceso'
     * (Visibilidad para el cliente).
     */
    @Override
    @Transactional
    public OrdenReparacion abrirDesdeCita(UUID idCita, UUID idTrabajador) {
        // Evitar duplicados
        if (orRepository.existsByCitaIdCita(idCita)) {
            throw new RuntimeException("Ya existe una orden de trabajo activa para esta cita.");
        }

        Cita cita = citaRepository.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        Trabajador trabajador = trabajadorRepository.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));

        // 1. Cambiar estado de la Cita a EN PROCESO
        cita.setEstado(Cita.EstadoCita.En_proceso);
        citaRepository.save(cita);

        // 2. Crear nueva Orden de Reparación
        OrdenReparacion nuevaOrden = new OrdenReparacion();
        nuevaOrden.setCita(cita);
        nuevaOrden.setTrabajador(trabajador);
        nuevaOrden.setVehiculo(cita.getVehiculo());
        nuevaOrden.setEstadoRep(OrdenReparacion.EstadoRep.En_proceso);
        nuevaOrden.setFechaInicio(LocalDate.now());

        return orRepository.save(nuevaOrden);
    }

    /**
     * PASO 2: Finalizar el trabajo desde el panel de órdenes. La Cita pasa a
     * 'Completada' definitivamente.
     */
    @Override
    @Transactional
    public OrdenReparacion finalizarOrden(UUID id, OrdenReparacion datosNuevos) {
        // 1. Buscar la orden
        OrdenReparacion or = orRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // 2. Actualizar datos técnicos de la orden
        or.setDiagnostico(datosNuevos.getDiagnostico());
        or.setHoras(datosNuevos.getHoras());
        or.setEstadoRep(OrdenReparacion.EstadoRep.Completada);

        // --- AQUÍ DEBES PONER LA FECHA ---
        or.setFechaFin(LocalDate.now()); 
        // ---------------------------------

        // 3. Cambiar estado de la Cita vinculada a COMPLETADA
        if (or.getCita() != null) {
            Cita citaAsociada = or.getCita();
            citaAsociada.setEstado(Cita.EstadoCita.Completada);
            citaRepository.save(citaAsociada);
        }

        return orRepository.save(or);
    }
}
