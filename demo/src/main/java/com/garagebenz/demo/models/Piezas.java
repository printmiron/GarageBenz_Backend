package com.garagebenz.demo.models;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "Piezas")
public class Piezas {

    @Id
    @Column(name = "id_pieza", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID idPieza;

    @Column(name = "Nombre", nullable = false, length = 150)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @PrePersist
    public void prePersist() {
        if (idPieza == null) {
            idPieza = UUID.randomUUID();
        }
    }
}
