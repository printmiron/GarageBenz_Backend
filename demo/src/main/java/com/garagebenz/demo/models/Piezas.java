package com.garagebenz.demo.models;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Piezas")
public class Piezas {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR) 
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

    public UUID getIdPieza() {
        return idPieza;
    }

    public void setIdPieza(UUID idPieza) {
        this.idPieza = idPieza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
