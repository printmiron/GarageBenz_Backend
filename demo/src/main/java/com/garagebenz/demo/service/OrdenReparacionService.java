package com.garagebenz.demo.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.garagebenz.demo.models.Cita;
import com.garagebenz.demo.models.Cliente;
import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.models.Trabajador;
import com.garagebenz.demo.models.Vehiculo;
import com.garagebenz.demo.repository.CitaRepository;
import com.garagebenz.demo.repository.ClienteRepository;
import com.garagebenz.demo.repository.OrdenReparacionRepository;
import com.garagebenz.demo.repository.TrabajadorRepository;
import com.garagebenz.demo.repository.VehiculoRepository;


@Service
public class OrdenReparacionService {

    private final OrdenReparacionRepository ordenRepo;
    private final CitaRepository citaRepo;
    private final VehiculoRepository vehiculoRepo;
    private final TrabajadorRepository trabajadorRepo;

    // Inyección por constructor (buena práctica)
    public OrdenReparacionService(
            OrdenReparacionRepository ordenRepo,
            CitaRepository citaRepo,
            VehiculoRepository vehiculoRepo,
            TrabajadorRepository trabajadorRepo
    ) {
        this.ordenRepo = ordenRepo;
        this.citaRepo = citaRepo;
        this.vehiculoRepo = vehiculoRepo;
        this.trabajadorRepo = trabajadorRepo;
    }

    /**
     * Crea una orden de reparación a partir de una cita existente
     */
    public OrdenReparacion crearOrden(
            UUID idCita,
            UUID idVehiculo,
            UUID idTrabajador,
            String diagnostico
    ) {

        // 1️⃣ Obtener la cita (de aquí sale el cliente)
        Cita cita = citaRepo.findById(idCita)
                .orElseThrow(() -> new RuntimeException("Cita no existe"));

        // 2️⃣ Obtener vehículo
        Vehiculo vehiculo = vehiculoRepo.findById(idVehiculo)
                .orElseThrow(() -> new RuntimeException("Vehículo no existe"));

        // 3️⃣ Obtener trabajador
        Trabajador trabajador = trabajadorRepo.findById(idTrabajador)
                .orElseThrow(() -> new RuntimeException("Trabajador no existe"));

        // 4️⃣ Crear la orden
        OrdenReparacion orden = new OrdenReparacion();
        orden.setCita(cita);
        orden.setVehiculo(vehiculo);
        orden.setTrabajador(trabajador);
        orden.setDiagnostico(diagnostico);

        // 5️⃣ Guardar en base de datos
        return ordenRepo.save(orden);
    }
}


