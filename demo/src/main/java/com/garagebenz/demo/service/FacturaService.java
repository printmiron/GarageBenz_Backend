package com.garagebenz.demo.service;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.garagebenz.demo.models.Factura;
import com.garagebenz.demo.models.OrdenReparacion;
import com.garagebenz.demo.models.OrdenesPieza;
import com.garagebenz.demo.models.Stock;
import com.garagebenz.demo.repository.FacturaRepository;
import com.garagebenz.demo.repository.OrdenReparacionRepository;
import com.garagebenz.demo.repository.StockRepository;

import jakarta.transaction.Transactional;

@Service
public class FacturaService {

    @Autowired
    private FacturaRepository facturaRepo;
    @Autowired
    private OrdenReparacionRepository ordenRepo;
    @Autowired
    private StockRepository stockRepo; // Asumo que tienes este repositorio

    @Transactional
    public Factura generarFactura(UUID idOr) {
        // 1. Recuperar con los datos más recientes
        OrdenReparacion or = ordenRepo.findById(idOr)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada"));

        // 2. DESCONTAR STOCK REAL
        if (or.getPiezas() != null) {
            for (OrdenesPieza op : or.getPiezas()) {
                Stock stock = stockRepo.findByPiezaIdPieza(op.getPieza().getIdPieza())
                        .orElseThrow(() -> new RuntimeException("Sin stock para: " + op.getPieza().getNombre()));

                if (stock.getCantidad() < op.getCantidadUsada()) {
                    throw new RuntimeException("Stock insuficiente para: " + op.getPieza().getNombre());
                }

                stock.setCantidad(stock.getCantidad() - op.getCantidadUsada());
                stockRepo.save(stock);
            }
        }

        // 3. CALCULAR TOTALES (Suma de piezas + Mano de obra)
        double sumaPiezas = 0;
        if (or.getPiezas() != null) {
            sumaPiezas = or.getPiezas().stream()
                    .mapToDouble(op -> op.getPieza().getPrecio().doubleValue() * op.getCantidadUsada())
                    .sum();
        }

        double totalManoObra = (or.getHoras() != null ? or.getHoras().doubleValue() : 0) * 40.0;
        double subtotal = sumaPiezas + totalManoObra;
        double iva = subtotal * 0.21;

        // 4. CREAR FACTURA
        Factura f = new Factura();
        f.setOrdenReparacion(or);
        f.setFechaEmision(LocalDate.now());
        f.setNumeroFactura("FAC-" + String.format("%06d", System.currentTimeMillis() % 1000000));
        f.setTotalPiezas(sumaPiezas);
        f.setTotalManoObra(totalManoObra);
        f.setTotalIVA(iva);
        f.setImporteTotal(subtotal + iva);

        // 5. CERRAR ORDEN Y ASIGNAR FECHA (Para que el cliente la vea)
        or.setEstadoRep(OrdenReparacion.EstadoRep.Completada);
        or.setFechaFin(LocalDate.now());
        ordenRepo.save(or);

        return facturaRepo.save(f);
    }
}
