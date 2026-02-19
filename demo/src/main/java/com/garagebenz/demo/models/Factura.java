package com.garagebenz.demo.models;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "Facturas")
public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private Long idFactura;

    @Column(name = "numero_factura", nullable = false, unique = true)
    private String numeroFactura;

    @Column(name = "fecha_emision", nullable = false)
    private LocalDate fechaEmision;

    @Column(name = "total_piezas")
    private Double totalPiezas;

    @Column(name = "total_mano_obra")
    private Double totalManoObra;

    @Column(name = "total_iva") 
    private Double totalIVA;

    @Column(name = "importe_total")
    private Double importeTotal;

    @OneToOne
    @JoinColumn(name = "id_or", referencedColumnName = "id_or")
    @JsonManagedReference 
    private OrdenReparacion ordenReparacion;

    // --- GETTERS Y SETTERS ---

    public Long getIdFactura() { return idFactura; }
    public void setIdFactura(Long idFactura) { this.idFactura = idFactura; }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public LocalDate getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDate fechaEmision) { this.fechaEmision = fechaEmision; }

    public Double getTotalPiezas() { return totalPiezas; }
    public void setTotalPiezas(Double totalPiezas) { this.totalPiezas = totalPiezas; }

    public Double getTotalManoObra() { return totalManoObra; }
    public void setTotalManoObra(Double totalManoObra) { this.totalManoObra = totalManoObra; }

    public Double getTotalIVA() { return totalIVA; }
    public void setTotalIVA(Double totalIVA) { this.totalIVA = totalIVA; }

    public Double getImporteTotal() { return importeTotal; }
    public void setImporteTotal(Double importeTotal) { this.importeTotal = importeTotal; }

    public OrdenReparacion getOrdenReparacion() { return ordenReparacion; }
    public void setOrdenReparacion(OrdenReparacion ordenReparacion) { this.ordenReparacion = ordenReparacion; }
}