package com.garagebenz.demo.models;
import java.io.Serializable;


import java.util.UUID;

/**
 * Esta clase representa la CLAVE PRIMARIA COMPUESTA
 * de la tabla Ordenes_Pieza (id_or + id_pieza).
 *
 * NO es una entidad (@Entity)
 * NO se guarda como tabla
 * SOLO sirve para que JPA/Hibernate sepa
 * cómo identificar de forma única una fila.
 */
public class OrdenesPiezaId implements Serializable {

    // Deben llamarse IGUAL que los atributos @Id
    // de la entidad OrdenesPieza
    // y tener el MISMO TIPO (UUID, no la entidad)
    private UUID ordenReparacion;
    private UUID pieza;

    /**
     * Constructor vacío OBLIGATORIO.
     * Hibernate lo usa internamente cuando crea instancias
     * de la clave primaria.
     */
    public OrdenesPiezaId() {}

    /**
     * equals() define cuándo dos claves primarias
     * se consideran la MISMA.
     *
     * Dos filas son iguales si:
     *  - Tienen la misma orden de reparación
     *  - Y la misma pieza
     */
    @Override
    public boolean equals(Object o) {
        // Si es el mismo objeto en memoria, son iguales
        if (this == o) return true;

        // Si el objeto es null o de otra clase, no es igual
        if (!(o instanceof OrdenesPiezaId)) return false;

        // Convertimos el objeto para poder comparar campos
        OrdenesPiezaId that = (OrdenesPiezaId) o;

        // Comparación REAL de la PK compuesta
        return ordenReparacion.equals(that.ordenReparacion)
            && pieza.equals(that.pieza);
    }

    /**
     * hashCode() debe ser coherente con equals().
     *
     * Hibernate lo usa para:
     *  - Caches
     *  - Sets
     *  - Identidad interna de entidades
     *
     * Si dos objetos son iguales según equals(),
     * DEBEN devolver el mismo hashCode().
     */
    @Override
    public int hashCode() {
        return ordenReparacion.hashCode() + pieza.hashCode();
    }
}

