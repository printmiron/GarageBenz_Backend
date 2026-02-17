package com.garagebenz.demo.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ordenes_Reparacion")
public class OrdenReparacion {

    @Id
    @Column(name = "id_or", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID idOr;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_trabajador", nullable = false)
    private Trabajador trabajador;

    @Column(columnDefinition = "TEXT")
    private String diagnostico;

    @Column(precision = 5, scale = 2)
    private BigDecimal horas;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_rep", nullable = false)
    private EstadoRep estadoRep = EstadoRep.En_proceso;

    public enum EstadoRep {
        En_proceso,
        Completada,
        Pausada,
        Cancelada
    }

    @PrePersist
    public void prePersist() {
        if (idOr == null) {
            idOr = UUID.randomUUID();
        }
        if (estadoRep == null) {
            estadoRep = EstadoRep.En_proceso;
        }
    }

    // --- AÑADE ESTOS GETTERS PARA QUE SPRING GENERE EL JSON ---
    public UUID getIdOr() {
        return idOr;
    }

    public Cita getCita() {
        return cita;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public Trabajador getTrabajador() {
        return trabajador;
    }

    public String getDiagnostico() {
        return diagnostico;
    }

    public BigDecimal getHoras() {
        return horas;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public EstadoRep getEstadoRep() {
        return estadoRep;
    }

    // Setters que te faltaban
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstadoRep(EstadoRep estadoRep) {
        this.estadoRep = estadoRep;
    }

    public void setHoras(BigDecimal horas) {
        this.horas = horas;
    }

}
