package com.garagebenz.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.garagebenz.demo.models.Factura;
import com.garagebenz.demo.repository.FacturaRepository;
import com.garagebenz.demo.repository.OrdenReparacionRepository;
import com.garagebenz.demo.service.FacturaService;

@RestController
@RequestMapping("/facturas")
@CrossOrigin(origins = "http://localhost:4200")
public class FacturaController {

    @Autowired
    private FacturaService facturaService;

    @Autowired
    private FacturaRepository facturaRepo;

    @Autowired
    private OrdenReparacionRepository ordenRepo;

    // 1. GENERAR FACTURA: Cierra una orden y crea la factura
    @PostMapping("/generar/{idOr}")
    public ResponseEntity<Factura> crearFactura(@PathVariable UUID idOr) {
        return ResponseEntity.ok(facturaService.generarFactura(idOr));
    }

    // 2. LISTAR TODAS: Para que el admin vea el historial
    @GetMapping
    public List<Factura> listarTodas() {
        return facturaRepo.findAll();
    }

    // 3. ESTADÍSTICAS MENSUALES (EL DASHBOARD DEL ADMIN)
    @GetMapping("/stats/mensual/{mes}")
    public ResponseEntity<?> obtenerStatsMensuales(@PathVariable int mes) {
        Double ingresos = facturaRepo.sumarIngresosMensuales(mes);
        Double gastoStock = facturaRepo.sumarGastoStockMensual(mes);
        Long clientes = facturaRepo.contarClientesAtendidos(mes);

        // Devolvemos un objeto JSON con todos los datos sumados
        return ResponseEntity.ok(Map.of(
                "mes", mes,
                "totalIngresos", ingresos != null ? ingresos : 0.0,
                "totalGastoStock", gastoStock != null ? gastoStock : 0.0,
                "clientesAtendidos", clientes != null ? clientes : 0
        ));
    }
}
