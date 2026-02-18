package com.garagebenz.demo.models;
import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;

@Embeddable
public class OrdenesPiezaId implements Serializable {
    private UUID idOr;
    private UUID idPieza;

    public OrdenesPiezaId() {}
    public OrdenesPiezaId(UUID idOr, UUID idPieza) {
        this.idOr = idOr;
        this.idPieza = idPieza;
    }
    public UUID getIdOr() {
        return idOr;
    }
    public void setIdOr(UUID idOr) {
        this.idOr = idOr;
    }
    public UUID getIdPieza() {
        return idPieza;
    }
    public void setIdPieza(UUID idPieza) {
        this.idPieza = idPieza;
    }
    
    
}