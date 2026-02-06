package com.garagebenz.demo.models;

import java.util.UUID;
import jakarta.persistence.*;

@Entity
@Table(name = "Stock")
public class Stock {

    @Id
    @Column(name = "id_stock", columnDefinition = "CHAR(36)", nullable = false, updatable = false)
    private UUID idStock;

    @ManyToOne
    @JoinColumn(name = "id_pieza", nullable = false)
    private Piezas pieza;

    @Column(nullable = false)
    private int cantidad = 0;

    @PrePersist
    public void prePersist() {
        if (idStock == null) {
            idStock = UUID.randomUUID();
        }
    }
}

