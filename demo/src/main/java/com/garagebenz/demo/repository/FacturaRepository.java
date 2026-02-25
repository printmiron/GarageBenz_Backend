package com.garagebenz.demo.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.garagebenz.demo.models.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {

    Optional<Factura> findByOrdenReparacionIdOr(UUID idOr);

    // 1. Sumar todos los ingresos (Importe Total) de un mes
    @Query("SELECT SUM(f.importeTotal) FROM Factura f WHERE MONTH(f.fechaEmision) = :mes AND YEAR(f.fechaEmision) = YEAR(CURRENT_DATE)")
    Double sumarIngresosMensuales(@Param("mes") int mes);

    // 2. Sumar el gasto total en PIEZAS / STOCK de un mes
    @Query("SELECT SUM(f.totalPiezas) FROM Factura f WHERE MONTH(f.fechaEmision) = :mes")
    Double sumarGastoStockMensual(@Param("mes") int mes);

    // 3. Contar cuántos CLIENTES diferentes han sido atendidos en el mes

    @Query("SELECT COUNT(DISTINCT f.ordenReparacion.cita.cliente.idCliente) FROM Factura f WHERE MONTH(f.fechaEmision) = :mes")
    Long contarClientesAtendidos(@Param("mes") int mes);
}