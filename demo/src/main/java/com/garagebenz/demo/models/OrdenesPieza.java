package com.garagebenz.demo.models;

import java.sql.Types;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ordenes_Pieza")
public class OrdenesPieza {

    @EmbeddedId
    private OrdenesPiezaId id;

    // Relación con la Orden de Reparación
    @ManyToOne
    @MapsId("idOr") // Este nombre debe coincidir exactamente con el campo en OrdenesPiezaId
    @JoinColumn(name = "id_or", columnDefinition = "CHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    private OrdenReparacion orden;

    // Relación con la Pieza
    @ManyToOne
    @MapsId("idPieza") // Este nombre debe coincidir exactamente con el campo en OrdenesPiezaId
    @JoinColumn(name = "id_pieza", columnDefinition = "CHAR(36)")
    @JdbcTypeCode(Types.VARCHAR)
    private Piezas piezas;

    @Column(name = "cantidad_usada", nullable = false)
    private int cantidadUsada;

    public OrdenesPieza() {
    }

    // Constructor de conveniencia
    public OrdenesPieza(OrdenReparacion orden, Piezas pieza, int cantidadUsada) {
        this.id = new OrdenesPiezaId(orden.getIdOr(), pieza.getIdPieza());
        this.orden = orden;
        this.piezas = pieza;
        this.cantidadUsada = cantidadUsada;
    }

    // --- GETTERS Y SETTERS ---

    public OrdenesPiezaId getId() {
        return id;
    }

    public void setId(OrdenesPiezaId id) {
        this.id = id;
    }

    public OrdenReparacion getOrden() {
        return orden;
    }

    public void setOrden(OrdenReparacion orden) {
        this.orden = orden;
    }

    public Piezas getPieza() {
        return piezas;
    }

    public void setPieza(Piezas pieza) {
        this.piezas = pieza;
    }

    public int getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }
}