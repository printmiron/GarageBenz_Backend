package com.garagebenz.demo.models;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ordenes_Reparacion")
public class OrdenReparacion {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id_or", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID idOr;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    @JsonIgnoreProperties({"cliente", "vehiculo", "ordenes"}) // Evita que la cita traiga de nuevo al cliente
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    @JsonIgnoreProperties({"ordenes", "cliente", "citas"})
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_trabajador", nullable = false)
    @JsonIgnoreProperties({"contrasena", "rol", "usuario", "password"})
    private Trabajador trabajador;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(precision = 5, scale = 2)
    private BigDecimal horas;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_rep")
    private EstadoRep estadoRep = EstadoRep.En_proceso;

    public enum EstadoRep {
        En_proceso, Completada, Pausada, Cancelada
    }

    @OneToMany(mappedBy = "orden", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("orden") // Evita bucle Orden -> Pieza -> Orden
    private List<OrdenesPieza> piezas;

    @OneToOne(mappedBy = "ordenReparacion")
    @JsonBackReference // Evita bucle infinito con Factura
    private Factura factura;

    @PrePersist
    public void prePersist() {
        if (idOr == null) {
            idOr = UUID.randomUUID();
        }
        if (estadoRep == null) {
            estadoRep = EstadoRep.En_proceso;
        }
    }

    // --- GETTERS Y SETTERS ---
    public UUID getIdOr() {
        return idOr;
    }

    public void setIdOr(UUID idOr) {
        this.idOr = idOr;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public List<OrdenesPieza> getPiezas() {
        return piezas;
    }

    public void setPiezas(List<OrdenesPieza> piezas) {
        this.piezas = piezas;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public BigDecimal getHoras() {
        return horas;
    }

    public void setHoras(BigDecimal horas) {
        this.horas = horas;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoRep getEstadoRep() {
        return estadoRep;
    }

    public void setEstadoRep(EstadoRep estadoRep) {
        this.estadoRep = estadoRep;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }
}
