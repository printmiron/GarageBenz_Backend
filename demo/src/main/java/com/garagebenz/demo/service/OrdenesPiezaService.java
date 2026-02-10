package com.garagebenz.demo.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.models.OrdenesPieza;
import com.garagebenz.demo.models.OrdenesPiezaId;
import com.garagebenz.demo.models.Piezas;
import com.garagebenz.demo.repository.OrdenReparacionRepository;
import com.garagebenz.demo.repository.OrdenesPiezaRepository;
import com.garagebenz.demo.repository.PiezasRepository;

@Service
public class OrdenesPiezaService {

    private final OrdenesPiezaRepository ordenesPiezaRepo;
    private final OrdenReparacionRepository ordenRepo;
    private final PiezasRepository piezasRepo;

    // Inyección por constructor
    public OrdenesPiezaService(
            OrdenesPiezaRepository ordenesPiezaRepo,
            OrdenReparacionRepository ordenRepo,
            PiezasRepository piezasRepo
    ) {
        this.ordenesPiezaRepo = ordenesPiezaRepo;
        this.ordenRepo = ordenRepo;
        this.piezasRepo = piezasRepo;
    }

    /**
     * Añade una pieza a una orden de reparación
     */
    public OrdenesPieza agregarPieza(
            UUID idOrden,
            UUID idPieza,
            int cantidad
    ) {
        // 1️⃣ Obtener orden de reparación
        OrdenReparacion orden = ordenRepo.findById(idOrden)
                .orElseThrow(() -> new RuntimeException("Orden no existe"));

        // 2️⃣ Obtener pieza
        Piezas pieza = piezasRepo.findById(idPieza)
                .orElseThrow(() -> new RuntimeException("Pieza no existe"));

        // 3️⃣ Crear relación Orden - Pieza
        OrdenesPieza op = new OrdenesPieza();
        op.setOrdenReparacion(orden);
        op.setPieza(pieza);
        op.setCantidadUsada(cantidad);

        // 4️⃣ Guardar
        return ordenesPiezaRepo.save(op);
    }

    /**
     * Elimina una pieza de una orden
     */
    public void eliminarPieza(UUID idOrden, UUID idPieza) {
        OrdenesPiezaId id = new OrdenesPiezaId();
        id.setOrdenReparacion(idOrden);
        id.setPieza(idPieza);

        ordenesPiezaRepo.deleteById(id);
    }
}

