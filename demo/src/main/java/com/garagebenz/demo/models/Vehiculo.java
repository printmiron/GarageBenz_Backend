package com.garagebenz.demo.models;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Vehiculos")
public class Vehiculo {
    
    @Id
    @Column(name = "id_vehiculo", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID idVehiculo;

    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(nullable = false, unique = true, length = 17)
    private String vin;

    @Column(nullable = false, length = 100)
    private String modelo;

    @Column(name = "año", nullable = false)
    private int anio;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @PrePersist
    public void prePersist() {
        if (idVehiculo == null) {
            idVehiculo = UUID.randomUUID();
        }
    }
    
}
