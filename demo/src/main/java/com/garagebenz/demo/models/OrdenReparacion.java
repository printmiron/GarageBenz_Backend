package com.garagebenz.demo.models;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;
import jakarta.persistence.*;

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


}

