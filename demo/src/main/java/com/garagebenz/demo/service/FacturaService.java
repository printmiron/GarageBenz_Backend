package com.garagebenz.demo.service;

import java.time.LocalDate;

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
    public Factura generarFactura(OrdenReparacion or) {
        // 1. DESCONTAR STOCK REAL DE LAS PIEZAS USADAS
        if (or.getPiezas() != null) {
            for (OrdenesPieza op : or.getPiezas()) {
                // Buscamos el stock por el ID de la pieza
                Stock stock = stockRepo.findByPiezaIdPieza(op.getPieza().getIdPieza())
                        .orElseThrow(() -> new RuntimeException("Error: No existe registro de stock para " + op.getPieza().getNombre()));

                if (stock.getCantidad() < op.getCantidadUsada()) {
                    throw new RuntimeException("Stock insuficiente en el momento del cierre para: " + op.getPieza().getNombre());
                }

                // Restamos unidades
                stock.setCantidad(stock.getCantidad() - op.getCantidadUsada());
                stockRepo.save(stock);
            }
        }

        // 2. CERRAR LA ORDEN
        or.setEstadoRep(OrdenReparacion.EstadoRep.Completada);
        or.setFechaFin(LocalDate.now());
        ordenRepo.save(or);

        // 3. CALCULAR TOTALES Y CREAR FACTURA
        double sumaPiezas = or.getPiezas().stream()
                .mapToDouble(op -> op.getPieza().getPrecio().doubleValue() * op.getCantidadUsada())
                .sum();

        double manoObra = (or.getHoras() != null ? or.getHoras().doubleValue() : 0) * 40.0;
        double subtotal = sumaPiezas + manoObra;
        double iva = subtotal * 0.21;

        Factura f = new Factura();
        f.setOrdenReparacion(or);
        f.setFechaEmision(LocalDate.now());
        f.setNumeroFactura("INV-" + System.currentTimeMillis() % 1000000);
        f.setTotalPiezas(sumaPiezas);
        f.setTotalManoObra(manoObra);
        f.setTotalIVA(iva);
        f.setImporteTotal(subtotal + iva);

        return facturaRepo.save(f);
    }
}
