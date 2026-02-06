package com.garagebenz.demo.models;

import jakarta.persistence.*;

@Entity
@Table(name = "Ordenes_Pieza")
@IdClass(OrdenesPiezaId.class)
public class OrdenesPieza {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_or", nullable = false)
    private OrdenReparacion ordenReparacion;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_pieza", nullable = false)
    private Piezas pieza;

    @Column(name = "cantidad_usada", nullable = false)
    private int cantidadUsada;
}
