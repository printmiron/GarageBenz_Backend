package com.garagebenz.demo.models;

import java.time.LocalDate;
import java.time.LocalTime;
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
@Table(name = "Cita")
public class Cita {
    @Id
    @Column(name = "id_cita", columnDefinition = "CHAR(36)")
    private UUID idCita;

    @Column(name = "hora_cita", nullable = false)
    private LocalTime horaCita;
      @Column(name = "fecha_cita", nullable = false)
    private LocalDate fechaCita;

    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCita estado = EstadoCita.Pendiente;

    public enum EstadoCita {
        Pendiente,
        Confirmada,
        Completada,
        Cancelada
    }

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_vehiculo", nullable = false)
    private Vehiculo vehiculo;

    @PrePersist
    public void prePersist() {
        if (idCita == null) {
            idCita = UUID.randomUUID();
        }
    }

}
