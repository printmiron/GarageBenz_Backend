package com.garagebenz.demo.models;

import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "Stock")
public class Stock {

    @Id
    @JdbcTypeCode(SqlTypes.CHAR) 
    @Column(name = "id_stock", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID idStock;

    @ManyToOne
    @JoinColumn(name = "id_pieza", nullable = false)
    private Piezas pieza;

    @Column(nullable = false)
    private Integer cantidad = 0;

    @PrePersist
    public void prePersist() {
        if (idStock == null) {
            idStock = UUID.randomUUID();
        }
    }

    public UUID getIdStock() {
        return idStock;
    }

    public void setIdStock(UUID idStock) {
        this.idStock = idStock;
    }

    public Piezas getPieza() {
        return pieza;
    }

    public void setPieza(Piezas pieza) {
        this.pieza = pieza;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    
}

