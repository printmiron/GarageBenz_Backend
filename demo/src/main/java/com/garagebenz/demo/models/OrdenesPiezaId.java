package com.garagebenz.demo.models;

import java.io.Serializable;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

import org.hibernate.annotations.JdbcTypeCode;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class OrdenesPiezaId implements Serializable {

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id_or", columnDefinition = "CHAR(36)")
    private UUID idOr;

    @JdbcTypeCode(Types.VARCHAR)
    @Column(name = "id_pieza", columnDefinition = "CHAR(36)")
    private UUID idPieza;

    public OrdenesPiezaId() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrdenesPiezaId that = (OrdenesPiezaId) o;
        return Objects.equals(idOr, that.idOr) && Objects.equals(idPieza, that.idPieza);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idOr, idPieza);
    }
}
