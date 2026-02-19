package com.garagebenz.demo.models;

import java.math.BigDecimal;
import java.sql.Types;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id_or", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID idOr;

    @ManyToOne
    @JoinColumn(name = "id_cita", nullable = false)
    @JsonIgnoreProperties("cliente")
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    @JsonIgnoreProperties({"ordenes", "cliente"})
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "id_trabajador", nullable = false)
    @JsonIgnoreProperties({"contrasena", "rol", "usuario"})
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

    // --- GETTERS ---
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

    // --- SETTERS ---
    public void setIdOr(UUID idOr) {
        this.idOr = idOr;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public void setTrabajador(Trabajador trabajador) {
        this.trabajador = trabajador;
    }

    public void setDiagnostico(String diagnostico) {
        this.diagnostico = diagnostico;
    }

    public void setHoras(BigDecimal horas) {
        this.horas = horas;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstadoRep(EstadoRep estadoRep) {
        this.estadoRep = estadoRep;
    }

    public Optional<Piezas> findById(UUID idOr2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}
