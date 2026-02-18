package com.garagebenz.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ordenes_Pieza")
public class OrdenesPieza {

    @EmbeddedId
    private OrdenesPiezaId id;

    @Column(name = "cantidad_usada", nullable = false)
    private int cantidadUsada;

    // Getters y Setters
    public OrdenesPiezaId getId() {
        return id;
    }

    public void setId(OrdenesPiezaId id) {
        this.id = id;
    }

    public int getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }
}
