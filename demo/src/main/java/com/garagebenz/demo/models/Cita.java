package com.garagebenz.demo.models;

import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Cita")
public class Cita {

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id_cita", columnDefinition = "CHAR(36)")
    private UUID idCita;

    @Column(name = "fecha_cita", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCita;

    @Column(name = "hora_cita", nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime horaCita;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado = EstadoCita.Pendiente;

    public enum EstadoCita {
        Pendiente, Confirmada, En_proceso, Completada, Cancelada
    }

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    @JsonBackReference(value = "cliente-cita")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    @JsonIgnoreProperties({"cliente", "citas"})
    private Vehiculo vehiculo;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, orphanRemoval = true)

    @JsonIgnore 
    private List<OrdenReparacion> ordenes;


    @PrePersist
    public void prePersist() {
        if (idCita == null) {
            idCita = UUID.randomUUID();
        }
    }


    public UUID getIdCita() {
        return idCita;
    }

    public void setIdCita(UUID idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(LocalDate fechaCita) {
        this.fechaCita = fechaCita;
    }

    public LocalTime getHoraCita() {
        return horaCita;
    }

    public void setHoraCita(LocalTime horaCita) {
        this.horaCita = horaCita;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(Vehiculo vehiculo) {
        this.vehiculo = vehiculo;
    }

    public List<OrdenReparacion> getOrdenes() {
        return ordenes;
    }

    public void setOrdenes(List<OrdenReparacion> ordenes) {
        this.ordenes = ordenes;
    }
}
